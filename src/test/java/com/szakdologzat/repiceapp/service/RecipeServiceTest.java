package com.szakdologzat.repiceapp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.repository.FavoriteRelationRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import com.szakdologzat.repiceapp.service.mapper.RatingMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import com.szakdologzat.repiceapp.service.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserService userService;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private FavoriteRelationRepository favoriteRelationRepository;

    @InjectMocks
    private RecipeService recipeService;

    private List<Recipe> mockRecipes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setTitle("Recipe 1");
        recipe1.setFoodCategory(FoodCategory.CHINESE);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setTitle("Recipe 2");
        recipe2.setFoodCategory(FoodCategory.ITALIAN);

        mockRecipes = List.of(recipe1, recipe2);

        // Mock repository behavior
        Mockito.when(recipeRepository.findAll()).thenReturn(mockRecipes);
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        Mockito.when(recipeRepository.findById(2L)).thenReturn(Optional.of(recipe2));
        Mockito.when(recipeRepository.findSameFoodCategoryRecipes(FoodCategory.CHINESE)).thenReturn(List.of(recipe1));
    }

    @Test
    void testIsFavoriteByUser() {
        // Mock the user and repository behavior
        User mockUser = new User();
        mockUser.setId(1L);
        Mockito.when(userService.getUserWithAuthorities()).thenReturn(Optional.of(mockUser));
        Mockito.when(favoriteRelationRepository.existsByUserIdAndRecipeId(1L, 100L)).thenReturn(true);

        // Call the method under test
        Boolean isFavorite = recipeService.isFavoriteByUser(100L);

        // Verify the result
        assertTrue(isFavorite, "The recipe should be marked as favorite by the user");

        // Verify interactions
        Mockito.verify(favoriteRelationRepository).existsByUserIdAndRecipeId(1L, 100L);
    }

    @Test
    void testSaveRecipe() {
        // Step 1: Create mock user (this will be returned by userService.getUserWithAuthorities())
        User mockUser = new User();
        mockUser.setId(1L);

        // Mock the user service to return the mock user wrapped in an Optional
        Mockito.when(userService.getUserWithAuthorities()).thenReturn(Optional.of(mockUser));

        // Step 2: Create a mock RecipeDTO to be saved
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setTitle("Mock Recipe");

        // Step 3: Create a mock Recipe entity that corresponds to the RecipeDTO
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setTitle("Mock Recipe");

        // Step 4: Mock the repository and mapper behavior
        Mockito.when(recipeMapper.toEntity(recipeDTO)).thenReturn(mockRecipe); // RecipeDTO -> Recipe
        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(mockRecipe); // Save recipe
        Mockito.when(recipeMapper.toDto(mockRecipe)).thenReturn(recipeDTO); // Recipe -> RecipeDTO

        // Step 5: Mock userMapper to return a DTO representation of the mock user
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setId(1L);
        Mockito.when(userMapper.userToUserDTO(mockUser)).thenReturn(mockUserDTO);

        // Step 6: Call the service method and get the result
        RecipeDTO savedRecipe = recipeService.save(recipeDTO);

        // Step 7: Verify the result
        assertEquals(recipeDTO.getId(), savedRecipe.getId());
        assertEquals(recipeDTO.getTitle(), savedRecipe.getTitle());

        // Step 8: Verify interactions with mocks
        Mockito.verify(recipeRepository).save(mockRecipe);
        Mockito.verify(recipeMapper).toEntity(recipeDTO);
        Mockito.verify(recipeMapper).toDto(mockRecipe);
        Mockito.verify(userService).getUserWithAuthorities(); // Verify userService is called
        Mockito.verify(userMapper).userToUserDTO(mockUser); // Verify that the user was mapped to DTO
    }

    @Test
    void testPartialUpdateRecipe() {
        // Mock input and existing data
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setTitle("Partially Updated Recipe");

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(1L);
        existingRecipe.setTitle("Original Recipe");

        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(1L);
        updatedRecipe.setTitle("Partially Updated Recipe");

        RecipeDTO updatedRecipeDTO = new RecipeDTO();
        updatedRecipeDTO.setId(1L);
        updatedRecipeDTO.setTitle("Partially Updated Recipe");

        // Mock behavior
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(existingRecipe));
        Mockito.doAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            recipe.setTitle(recipeDTO.getTitle());
            return null;
        })
            .when(recipeMapper)
            .partialUpdate(existingRecipe, recipeDTO);
        Mockito.when(recipeRepository.save(existingRecipe)).thenReturn(updatedRecipe);
        Mockito.when(recipeMapper.toDto(updatedRecipe)).thenReturn(updatedRecipeDTO);

        // Call the method under test
        Optional<RecipeDTO> result = recipeService.partialUpdate(recipeDTO);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipe -> assertEquals("Partially Updated Recipe", recipe.getTitle()));

        // Verify interactions
        Mockito.verify(recipeRepository).findById(1L);
        Mockito.verify(recipeMapper).partialUpdate(existingRecipe, recipeDTO);
        Mockito.verify(recipeRepository).save(existingRecipe);
        Mockito.verify(recipeMapper).toDto(updatedRecipe);
    }

    @Test
    void testFindSameFoodCategoryRecipes() {
        // Mock data for CHINESE food category
        FoodCategory foodCategory = FoodCategory.CHINESE;
        List<Recipe> chineseRecipes = List.of(
            new Recipe(1L, "CHINESE Recipe 1", foodCategory),
            new Recipe(2L, "CHINESE Recipe 2", foodCategory)
        );

        // Mock behavior
        Mockito.when(recipeRepository.findSameFoodCategoryRecipes(foodCategory)).thenReturn(chineseRecipes);
        Mockito.when(recipeMapper.toDto(Mockito.any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return new RecipeDTO(recipe.getId(), recipe.getTitle());
        });

        // Call the method
        List<RecipeDTO> result = recipeService.findSameFoodCategoryRecipes(foodCategory);

        // Assertions
        assertEquals(2, result.size());
        assertEquals("CHINESE Recipe 1", result.get(0).getTitle());
        assertEquals("CHINESE Recipe 2", result.get(1).getTitle());

        // Verify interactions
        Mockito.verify(recipeRepository).findSameFoodCategoryRecipes(foodCategory);
        Mockito.verify(recipeMapper, Mockito.times(2)).toDto(Mockito.any(Recipe.class));
    }

    @Test
    void findAll() {
        // Mock behavior
        Mockito.when(recipeMapper.toDto(Mockito.any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return new RecipeDTO(recipe.getId(), recipe.getTitle());
        });

        // Call the method
        List<RecipeDTO> result = recipeService.findAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getTitle());
        assertEquals("Recipe 2", result.get(1).getTitle());

        // Verify interactions
        Mockito.verify(recipeRepository).findAll();
        Mockito.verify(recipeMapper, Mockito.times(2)).toDto(Mockito.any(Recipe.class));
    }

    @Test
    void testFindLatestRecipes() {
        // Mock behavior
        Mockito.when(recipeRepository.findLatestRecipes()).thenReturn(mockRecipes);
        Mockito.when(recipeMapper.toDto(Mockito.any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return new RecipeDTO(recipe.getId(), recipe.getTitle());
        });

        // Call the method
        List<RecipeDTO> result = recipeService.findLatestRecipes();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getTitle());
        assertEquals("Recipe 2", result.get(1).getTitle());

        // Verify interactions
        Mockito.verify(recipeRepository).findLatestRecipes();
        Mockito.verify(recipeMapper, Mockito.times(2)).toDto(Mockito.any(Recipe.class));
    }

    @Test
    void testFindHighestFavoriteCounts() {
        // Mock behavior
        Mockito.when(recipeRepository.findHighestFavoriteCounts()).thenReturn(mockRecipes);
        Mockito.when(recipeMapper.toDto(Mockito.any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return new RecipeDTO(recipe.getId(), recipe.getTitle());
        });

        // Call the method
        List<RecipeDTO> result = recipeService.findHighestFavoriteCounts();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getTitle());
        assertEquals("Recipe 2", result.get(1).getTitle());

        // Verify interactions
        Mockito.verify(recipeRepository).findHighestFavoriteCounts();
        Mockito.verify(recipeMapper, Mockito.times(2)).toDto(Mockito.any(Recipe.class));
    }

    @Test
    void testFindRecipeRatings() {
        // Mock data
        Long recipeId = 1L;
        Rating rating1 = new Rating(1L, 5, "Great!");
        Rating rating2 = new Rating(2L, 4, "Good!");
        List<Rating> ratings = List.of(rating1, rating2);

        RatingDTO ratingDTO1 = new RatingDTO(1L, 5, "Great!");
        RatingDTO ratingDTO2 = new RatingDTO(2L, 4, "Good!");

        // Mock behavior
        Mockito.when(recipeRepository.findRecipeRatings(recipeId)).thenReturn(Optional.of(ratings));
        Mockito.when(ratingMapper.toDto(rating1)).thenReturn(ratingDTO1);
        Mockito.when(ratingMapper.toDto(rating2)).thenReturn(ratingDTO2);

        // Call the method
        Optional<List<RatingDTO>> result = recipeService.findRecipeRatings(recipeId);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipeRatings -> {
            assertEquals(2, recipeRatings.size(), "The number of ratings should match");
            assertEquals("Great!", recipeRatings.get(0).getTitle(), "The first rating title should match");
            assertEquals("Good!", recipeRatings.get(1).getTitle(), "The second rating title should match");
        });

        // Verify interactions
        Mockito.verify(recipeRepository).findRecipeRatings(recipeId);
        Mockito.verify(ratingMapper).toDto(rating1);
        Mockito.verify(ratingMapper).toDto(rating2);
    }

    @Test
    void testDeleteRecipe() {
        // Mock repository behavior
        Mockito.when(recipeRepository.isUserTheOwner(1L)).thenReturn(true);

        // Call the service method
        ResponseEntity<?> response = recipeService.delete(1L);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Recipe has been deleted", response.getBody());

        // Verify repository interaction
        Mockito.verify(recipeRepository).deleteById(1L);
    }

    @Test
    void testSearchRecipes() {
        // Mock input parameters
        String recipeTitle = "Delicious";
        List<String> foodCategories = List.of("CHINESE", "ITALIAN");
        List<String> foodTypes = List.of("VEGAN", "VEGETARIAN");
        List<String> ingredientNames = List.of("garlic", "onion");
        Pageable pageable = PageRequest.of(0, 10);

        // Mock output data
        Recipe recipe1 = new Recipe(1L, "Delicious Recipe 1", FoodCategory.CHINESE);
        Recipe recipe2 = new Recipe(2L, "Delicious Recipe 2", FoodCategory.ITALIAN);
        List<Recipe> mockRecipes = List.of(recipe1, recipe2);
        Page<Recipe> mockPage = new PageImpl<>(mockRecipes);

        // Mock repository behavior
        Mockito.when(recipeRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(mockPage);

        // Call the service method
        Page<Recipe> result = recipeService.searchRecipes(recipeTitle, foodCategories, foodTypes, ingredientNames, pageable);

        // Assertions
        Assertions.assertEquals(2, result.getTotalElements());
        Assertions.assertEquals("Delicious Recipe 1", result.getContent().get(0).getTitle());
        Assertions.assertEquals(FoodCategory.CHINESE, result.getContent().get(0).getFoodCategory());
        Assertions.assertEquals("Delicious Recipe 2", result.getContent().get(1).getTitle());
        Assertions.assertEquals(FoodCategory.ITALIAN, result.getContent().get(1).getFoodCategory());

        // Verify interactions with the repository
        Mockito.verify(recipeRepository).findAll(Mockito.any(Specification.class), Mockito.eq(pageable));
    }
}
