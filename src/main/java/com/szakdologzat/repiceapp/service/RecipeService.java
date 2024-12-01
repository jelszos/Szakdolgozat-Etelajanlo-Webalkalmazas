package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.repository.FavoriteRelationRepository;
import com.szakdologzat.repiceapp.repository.IngredientRepository;
import com.szakdologzat.repiceapp.repository.InstructionRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.mapper.*;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.Recipe}.
 */
@Service
@Transactional
public class RecipeService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final InstructionRepository instructionRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final InstructionMapper instructionMapper;
    private final FavoriteRelationRepository favoriteRelationRepository;

    public RecipeService(
        RecipeRepository recipeRepository,
        RecipeMapper recipeMapper,
        RatingMapper ratingMapper,
        UserService userService,
        UserMapper userMapper,
        InstructionRepository instructionRepository,
        IngredientRepository ingredientRepository,
        IngredientMapper ingredientMapper,
        InstructionMapper instructionMapper,
        FavoriteRelationRepository favoriteRelationRepository
    ) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ratingMapper = ratingMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.instructionRepository = instructionRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.instructionMapper = instructionMapper;
        this.favoriteRelationRepository = favoriteRelationRepository;
    }

    public Boolean isFavoriteByUser(Long recipeId) {
        Optional<User> loggedInUser = userService.getUserWithAuthorities();
        return loggedInUser.filter(user -> favoriteRelationRepository.existsByUserIdAndRecipeId(user.getId(), recipeId)).isPresent();
    }

    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public RecipeDTO save(RecipeDTO recipeDTO) {
        LOG.debug("Request to save Recipe : {}", recipeDTO);

        // Associate user with recipeDTO or throw an exception if the user isn't found
        recipeDTO.setUser(
            userService.getUserWithAuthorities().map(userMapper::userToUserDTO).orElseThrow(() -> new RuntimeException("User not found"))
        );

        // Map RecipeDTO to Recipe, save it, and map back to RecipeDTO
        RecipeDTO savedRecipeDTO = recipeMapper.toDto(recipeRepository.save(recipeMapper.toEntity(recipeDTO)));

        // If instructions are present, iterate and save them with their ingredients
        if (recipeDTO.getInstructions() != null) {
            recipeDTO
                .getInstructions()
                .forEach(instructionDTO -> {
                    instructionDTO.setRecipe(savedRecipeDTO); // Associate saved recipe with each instruction

                    // Save instruction entity
                    InstructionDTO savedInstructionDTO = instructionMapper.toDto(
                        instructionRepository.save(instructionMapper.toEntity(instructionDTO))
                    );

                    // Set ingredients for each instruction, if present, and save them
                    if (instructionDTO.getIngredients() != null) {
                        instructionDTO
                            .getIngredients()
                            .forEach(ingredientDTO -> {
                                ingredientDTO.setInstruction(savedInstructionDTO); // Associate saved instruction with each ingredient
                                ingredientRepository.save(ingredientMapper.toEntity(ingredientDTO));
                            });
                    }
                });
        }

        return savedRecipeDTO;
    }

    /**
     * Update a recipe.
     *
     * @param recipeDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeDTO update(RecipeDTO recipeDTO) {
        LOG.debug("Request to update Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    /**
     * Partially update a recipe.
     *
     * @param recipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecipeDTO> partialUpdate(RecipeDTO recipeDTO) {
        LOG.debug("Request to partially update Recipe : {}", recipeDTO);

        return recipeRepository
            .findById(recipeDTO.getId())
            .map(existingRecipe -> {
                recipeMapper.partialUpdate(existingRecipe, recipeDTO);

                return existingRecipe;
            })
            .map(recipeRepository::save)
            .map(recipeMapper::toDto);
    }

    /**
     * Get all the recipes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeDTO> findAll() {
        LOG.debug("Request to get all Recipes");
        return recipeRepository.findAll().stream().map(recipeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<RecipeDTO> findLatestRecipes() {
        LOG.debug("Request to get most lately added Recipes");
        return recipeRepository.findLatestRecipes().stream().map(recipeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<RecipeDTO> findHighestFavoriteCounts() {
        LOG.debug("Request to get highest favorite count Recipes");
        return recipeRepository
            .findHighestFavoriteCounts()
            .stream()
            .map(recipeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<RecipeDTO> findSameFoodCategoryRecipes(FoodCategory foodCategory) {
        LOG.debug("Request to get same food category Recipes");
        return recipeRepository
            .findSameFoodCategoryRecipes(foodCategory)
            .stream()
            .map(recipeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recipe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeDTO> findOne(Long id) {
        LOG.debug("Request to get Recipe : {}", id);
        return recipeRepository.findById(id).map(recipeMapper::toDto);
    }

    // Service Method
    @Transactional(readOnly = true)
    public Optional<List<RatingDTO>> findRecipeRatings(Long id) {
        LOG.debug("Request to get Ratings for Recipe ID: {}", id);

        // Fetch the ratings for the specified recipe and map to DTOs
        return recipeRepository
            .findRecipeRatings(id)
            .map(ratings -> ratings.stream().map(ratingMapper::toDto).collect(Collectors.toList()));
    }

    /**
     * Delete the recipe by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        LOG.debug("Request to delete Recipe : {}", id);
        if (!recipeRepository.isUserTheOwner(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User don't have permission to delete this recipe");
        }
        recipeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Recipe has been deleted");
    }

    public Page<Recipe> searchRecipes(
        String recipeTitle,
        List<String> foodCategories,
        List<String> foodTypes,
        List<String> ingredientNames,
        Pageable pageable
    ) {
        return recipeRepository.findAll(
            (root, query, criteriaBuilder) -> {
                query.distinct(true);

                Specification<Recipe> spec = Specification.where(null);

                if (recipeTitle != null && !recipeTitle.isEmpty()) {
                    spec = spec.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.like(criteriaBuilder1.lower(root1.get("title")), "%" + recipeTitle.toLowerCase() + "%")
                    );
                }

                //Food category
                if (foodCategories != null && !foodCategories.isEmpty()) {
                    spec = spec.and((root1, query1, criteriaBuilder1) -> root1.get("foodCategory").in(foodCategories));
                }

                //Food types
                if (foodTypes != null && !foodTypes.isEmpty()) {
                    spec = spec.and((root1, query1, criteriaBuilder1) -> root1.get("foodType").in(foodTypes));
                }

                if (ingredientNames != null && !ingredientNames.isEmpty()) {
                    // Create a disjunction for the ingredient names check
                    Specification<Recipe> ingredientSpec = Specification.where(null);
                    for (String ingredientName : ingredientNames) {
                        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
                            ingredientSpec = ingredientSpec.or((root1, query1, criteriaBuilder1) ->
                                criteriaBuilder1.like(
                                    criteriaBuilder1.lower(root1.get("ingredientNames")),
                                    "%" + ingredientName.toLowerCase() + "%"
                                )
                            );
                        }
                    }
                    // Combine the ingredient specification with the main specification
                    if (ingredientSpec != null) {
                        spec = spec.or(ingredientSpec);
                    }
                }

                return spec.toPredicate(root, query, criteriaBuilder);
            },
            pageable
        );
    }
}
