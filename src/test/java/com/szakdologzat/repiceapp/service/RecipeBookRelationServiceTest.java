package com.szakdologzat.repiceapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookRelationMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RecipeBookRelationServiceTest {

    @Mock
    private RecipeBookRelationRepository recipeBookRelationRepository;

    @Mock
    private RecipeBookRelationMapper recipeBookRelationMapper;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private RecipeBookMapper recipeBookMapper;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeBookRepository recipeBookRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecipeBookRelationService recipeBookRelationService;

    private RecipeBookRelationDTO mockRecipeBookRelationDTO;
    private RecipeBookRelation mockRecipeBookRelation;
    private RecipeBook mockRecipeBook;
    private Recipe mockRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock objects for Recipe, RecipeBook, and RecipeBookRelation
        mockRecipeBook = new RecipeBook();
        mockRecipeBook.setId(1L);
        mockRecipeBook.setTitle("Mock RecipeBook");

        mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setTitle("Mock Recipe");

        mockRecipeBookRelation = new RecipeBookRelation();
        mockRecipeBookRelation.setId(1L);
        mockRecipeBookRelation.setRecipeBook(mockRecipeBook);
        mockRecipeBookRelation.setRecipe(mockRecipe);

        RecipeBookDTO mockRecipeBookDTO = recipeBookMapper.toDto(mockRecipeBook);
        RecipeDTO mockRecipeDTO = recipeMapper.toDto(mockRecipe);

        mockRecipeBookRelationDTO = new RecipeBookRelationDTO();
        mockRecipeBookRelationDTO.setId(1L);
        mockRecipeBookRelationDTO.setRecipeBook(mockRecipeBookDTO);
        mockRecipeBookRelationDTO.setRecipe(mockRecipeDTO);
    }

    @Test
    void testSave() {
        // Mock the repository and mapper behavior
        when(recipeBookRelationMapper.toEntity(mockRecipeBookRelationDTO)).thenReturn(mockRecipeBookRelation);
        when(recipeBookRelationRepository.save(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelation);
        when(recipeBookRelationMapper.toDto(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelationDTO);

        // Call the method
        RecipeBookRelationDTO result = recipeBookRelationService.save(mockRecipeBookRelationDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(mockRecipeBookRelationDTO.getId(), result.getId());
        assertEquals(mockRecipeBookRelationDTO.getRecipeBook(), result.getRecipeBook());

        // Verify interactions
        verify(recipeBookRelationRepository).save(mockRecipeBookRelation);
        verify(recipeBookRelationMapper).toEntity(mockRecipeBookRelationDTO);
        verify(recipeBookRelationMapper).toDto(mockRecipeBookRelation);
    }

    @Test
    void testUpdate() {
        // Mock repository behavior
        when(recipeBookRelationMapper.toEntity(mockRecipeBookRelationDTO)).thenReturn(mockRecipeBookRelation);
        when(recipeBookRelationRepository.save(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelation);
        when(recipeBookRelationMapper.toDto(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelationDTO);

        // Call the method
        RecipeBookRelationDTO result = recipeBookRelationService.update(mockRecipeBookRelationDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(mockRecipeBookRelationDTO.getId(), result.getId());

        // Verify interactions
        verify(recipeBookRelationRepository).save(mockRecipeBookRelation);
        verify(recipeBookRelationMapper).toEntity(mockRecipeBookRelationDTO);
        verify(recipeBookRelationMapper).toDto(mockRecipeBookRelation);
    }

    @Test
    void testPartialUpdate() {
        // Mock the repository behavior
        when(recipeBookRelationRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBookRelation));
        when(recipeBookRelationRepository.save(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelation);
        when(recipeBookRelationMapper.toDto(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelationDTO);

        // Call the method
        Optional<RecipeBookRelationDTO> result = recipeBookRelationService.partialUpdate(mockRecipeBookRelationDTO);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipeBook -> assertEquals(mockRecipeBookRelationDTO.getId(), recipeBook.getId()));

        // Verify interactions
        verify(recipeBookRelationRepository).findById(1L);
        verify(recipeBookRelationRepository).save(mockRecipeBookRelation);
        verify(recipeBookRelationMapper).partialUpdate(any(), eq(mockRecipeBookRelationDTO));
        verify(recipeBookRelationMapper).toDto(mockRecipeBookRelation);
    }

    @Test
    void testFindAll() {
        // Mock the repository behavior
        List<RecipeBookRelation> recipeBookRelations = List.of(mockRecipeBookRelation);
        when(recipeBookRelationRepository.findAll()).thenReturn(recipeBookRelations);
        when(recipeBookRelationMapper.toDto(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelationDTO);

        // Call the method
        List<RecipeBookRelationDTO> result = recipeBookRelationService.findAll();

        // Assertions
        assertEquals(1, result.size());
        assertEquals(mockRecipeBookRelationDTO.getId(), result.get(0).getId());

        // Verify interactions
        verify(recipeBookRelationRepository).findAll();
        verify(recipeBookRelationMapper).toDto(mockRecipeBookRelation);
    }

    @Test
    void testFindOne() {
        // Mock the repository behavior
        when(recipeBookRelationRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBookRelation));
        when(recipeBookRelationMapper.toDto(mockRecipeBookRelation)).thenReturn(mockRecipeBookRelationDTO);

        // Call the method
        Optional<RecipeBookRelationDTO> result = recipeBookRelationService.findOne(1L);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipeBook -> assertEquals(mockRecipeBookRelationDTO.getId(), recipeBook.getId()));

        // Verify interactions
        verify(recipeBookRelationRepository).findById(1L);
        verify(recipeBookRelationMapper).toDto(mockRecipeBookRelation);
    }

    @Test
    void testDelete() {
        // Mock repository behavior
        doNothing().when(recipeBookRelationRepository).deleteById(1L);

        // Call the method
        recipeBookRelationService.delete(1L);

        // Verify interactions
        verify(recipeBookRelationRepository).deleteById(1L);
    }

    @Test
    void testAddOrRemoveRecipe() {
        // Mock repository behavior
        RecipeBookDTO mockRecipeBookDTO = recipeBookMapper.toDto(mockRecipeBook);

        // When the recipe is not in the recipe book
        when(recipeBookRelationRepository.isRecipeInRecipeBook(1L, 1L)).thenReturn(false);
        when(recipeBookRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBook));
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));
        when(recipeBookRelationRepository.save(any(RecipeBookRelation.class))).thenReturn(mockRecipeBookRelation);
        when(recipeBookMapper.toDtoWithIsRecipeInTheList(mockRecipeBook, 1L)).thenReturn(mockRecipeBookDTO);

        // Call the method to add a recipe
        ResponseEntity<?> resultAdd = recipeBookRelationService.addOrRemoveRecipe(1L, 1L);

        // Assertions for adding
        assertEquals(HttpStatus.CREATED, resultAdd.getStatusCode());

        // Verify the interaction for adding the recipe
        verify(recipeBookRelationRepository).isRecipeInRecipeBook(1L, 1L); // Verify 1 call for add
        verify(recipeBookRepository).findById(1L);
        verify(recipeRepository).findById(1L);
        verify(recipeBookRelationRepository).save(any(RecipeBookRelation.class));

        // Reset mocks for the next behavior scenario (removal)
        reset(recipeBookRelationRepository);

        // When the recipe is in the recipe book
        when(recipeBookRelationRepository.isRecipeInRecipeBook(1L, 1L)).thenReturn(true);

        // Call the method to remove a recipe
        ResponseEntity<?> resultRemove = recipeBookRelationService.addOrRemoveRecipe(1L, 1L);

        // Assertions for removing
        assertEquals(HttpStatus.NO_CONTENT, resultRemove.getStatusCode());

        // Verify the interaction for removing the recipe
        verify(recipeBookRelationRepository).isRecipeInRecipeBook(1L, 1L); // Verify 1 call for remove
        verify(recipeBookRepository).findById(1L);
        verify(recipeRepository).findById(1L);
        verify(recipeBookRelationRepository).deleteByRecipeAndRecipeBookId(1L, 1L);
    }

    @Test
    void testGetRecipesByRecipeBookId() {
        // Mock repository behavior
        when(recipeBookRelationRepository.findAllByRecipeBookId(1L)).thenReturn(Set.of(mockRecipeBookRelation));
        when(recipeMapper.toDto(mockRecipe)).thenReturn(new RecipeDTO(mockRecipe.getId(), mockRecipe.getTitle()));

        // Call the method
        ResponseEntity<?> result = recipeBookRelationService.getRecipesByRecipeBookId(1L);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeleteRecipeFromRecipeBook() {
        // Mock repository behavior
        when(recipeBookRelationRepository.isUserTheOwner(1L)).thenReturn(true);
        when(recipeBookRelationRepository.getRbrByRecipeBookIdAndRecipeId(1L, 1L)).thenReturn(Optional.of(mockRecipeBookRelation));
        doNothing().when(recipeBookRelationRepository).delete(mockRecipeBookRelation);

        // Call the method
        ResponseEntity<String> result = recipeBookRelationService.deleteRecipeFromRecipeBook(1L, 1L);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Verify interactions
        verify(recipeBookRelationRepository).delete(mockRecipeBookRelation);
    }
}
