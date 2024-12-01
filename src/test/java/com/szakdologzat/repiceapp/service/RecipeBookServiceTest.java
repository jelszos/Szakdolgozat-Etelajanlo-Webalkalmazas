package com.szakdologzat.repiceapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RecipeBookServiceTest {

    @Mock
    private RecipeBookRepository recipeBookRepository;

    @Mock
    private RecipeBookMapper recipeBookMapper;

    @Mock
    private RecipeBookRelationRepository recipeBookRelationRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecipeBookService recipeBookService;

    private RecipeBook mockRecipeBook;
    private RecipeBookDTO mockRecipeBookDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock RecipeBook and RecipeBookDTO
        mockRecipeBook = new RecipeBook();
        mockRecipeBook.setId(1L);
        mockRecipeBook.setTitle("Mock RecipeBook");

        mockRecipeBookDTO = new RecipeBookDTO();
        mockRecipeBookDTO.setId(1L);
        mockRecipeBookDTO.setTitle("Mock RecipeBook");

        // Mock user service
        User mockUser = new User();
        mockUser.setId(1L);
        when(userService.getUserWithAuthorities()).thenReturn(Optional.of(mockUser));
    }

    @Test
    void testSaveRecipeBook() {
        // Mock the repository and mapper behavior
        when(recipeBookMapper.toEntity(mockRecipeBookDTO)).thenReturn(mockRecipeBook);
        when(recipeBookRepository.save(mockRecipeBook)).thenReturn(mockRecipeBook);
        when(recipeBookMapper.toDto(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        RecipeBookDTO result = recipeBookService.save(mockRecipeBookDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(mockRecipeBookDTO.getId(), result.getId());
        assertEquals(mockRecipeBookDTO.getTitle(), result.getTitle());

        // Verify interactions
        verify(recipeBookRepository).save(mockRecipeBook);
        verify(recipeBookMapper).toEntity(mockRecipeBookDTO);
        verify(recipeBookMapper).toDto(mockRecipeBook);
        verify(userService).getUserWithAuthorities();
    }

    @Test
    void testUpdateRecipeBook() {
        // Mock repository behavior
        when(recipeBookMapper.toEntity(mockRecipeBookDTO)).thenReturn(mockRecipeBook);
        when(recipeBookRepository.save(mockRecipeBook)).thenReturn(mockRecipeBook);
        when(recipeBookMapper.toDto(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        RecipeBookDTO result = recipeBookService.update(mockRecipeBookDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(mockRecipeBookDTO.getId(), result.getId());
        assertEquals(mockRecipeBookDTO.getTitle(), result.getTitle());

        // Verify interactions
        verify(recipeBookRepository).save(mockRecipeBook);
        verify(recipeBookMapper).toEntity(mockRecipeBookDTO);
        verify(recipeBookMapper).toDto(mockRecipeBook);
    }

    @Test
    void testPartialUpdateRecipeBook() {
        // Mock behavior for existing RecipeBook
        when(recipeBookRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBook));
        when(recipeBookRepository.save(mockRecipeBook)).thenReturn(mockRecipeBook);
        when(recipeBookMapper.toDto(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        Optional<RecipeBookDTO> result = recipeBookService.partialUpdate(mockRecipeBookDTO);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipeBook -> assertEquals(mockRecipeBookDTO.getTitle(), recipeBook.getTitle()));

        // Verify interactions
        verify(recipeBookRepository).findById(1L);
        verify(recipeBookRepository).save(mockRecipeBook);
        verify(recipeBookMapper).partialUpdate(mockRecipeBook, mockRecipeBookDTO);
        verify(recipeBookMapper).toDto(mockRecipeBook);
    }

    @Test
    void testFindAll() {
        // Mock the repository behavior
        Pageable pageable = PageRequest.of(0, 10);
        List<RecipeBook> recipeBooks = List.of(mockRecipeBook);
        Page<RecipeBook> page = new PageImpl<>(recipeBooks);
        when(recipeBookRepository.findAllBySearch(anyString(), anyString(), anyString(), anyString(), eq(pageable))).thenReturn(page);
        when(recipeBookMapper.toDtoWithRecipeImages(any(RecipeBook.class))).thenReturn(mockRecipeBookDTO);

        // Call the method
        ResponseEntity<?> result = recipeBookService.findAll("", "", "", "", pageable);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof Page);
        assertEquals(1, ((Page<?>) result.getBody()).getTotalElements());

        // Verify interactions
        verify(recipeBookRepository).findAllBySearch(anyString(), anyString(), anyString(), anyString(), eq(pageable));
        verify(recipeBookMapper).toDtoWithRecipeImages(any(RecipeBook.class));
    }

    @Test
    void testFindOne() {
        // Mock behavior
        when(recipeBookRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBook));
        when(recipeBookMapper.toDto(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        Optional<RecipeBookDTO> result = recipeBookService.findOne(1L);

        // Assertions
        assertTrue(result.isPresent());
        result.ifPresent(recipeBook -> assertEquals(mockRecipeBookDTO.getId(), recipeBook.getId()));

        // Verify interactions
        verify(recipeBookRepository).findById(1L);
        verify(recipeBookMapper).toDto(mockRecipeBook);
    }

    @Test
    void testDeleteRecipeBook() {
        // Mock repository behavior
        doNothing().when(recipeBookRepository).deleteById(1L);

        // Call the method
        recipeBookService.delete(1L);

        // Verify interactions
        verify(recipeBookRepository).deleteById(1L);
    }

    @Test
    void testChangeStatus() {
        // Mock behavior
        when(recipeBookRepository.findById(1L)).thenReturn(Optional.of(mockRecipeBook));
        when(recipeBookRepository.save(mockRecipeBook)).thenReturn(mockRecipeBook);
        when(recipeBookMapper.toDto(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        ResponseEntity<RecipeBookDTO> result = recipeBookService.changeStatus(1L);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());

        // Verify interactions
        verify(recipeBookRepository).findById(1L);
        verify(recipeBookRepository).save(mockRecipeBook);
        verify(recipeBookMapper).toDto(mockRecipeBook);
    }

    @Test
    void testFindAllByRecipeId() {
        // Mock behavior
        List<RecipeBookDTO> recipeBookDTOs = List.of(mockRecipeBookDTO);
        when(recipeBookRepository.findByUserIsCurrentUser()).thenReturn(List.of(mockRecipeBook));
        when(recipeBookMapper.toDtoWithIsRecipeInTheList(mockRecipeBook, 1L)).thenReturn(mockRecipeBookDTO);

        // Call the method
        List<RecipeBookDTO> result = recipeBookService.findAllByRecipeId(1L);

        // Assertions
        assertEquals(1, result.size());
        assertEquals(mockRecipeBookDTO.getId(), result.get(0).getId());

        // Verify interactions
        verify(recipeBookRepository).findByUserIsCurrentUser();
        verify(recipeBookMapper).toDtoWithIsRecipeInTheList(mockRecipeBook, 1L);
    }

    @Test
    void testFindAllByUser() {
        // Mock behavior
        List<RecipeBookDTO> recipeBookDTOs = List.of(mockRecipeBookDTO);
        when(recipeBookRepository.findByUserIsCurrentUser()).thenReturn(List.of(mockRecipeBook));
        when(recipeBookMapper.toDtoWithRecipeImages(mockRecipeBook)).thenReturn(mockRecipeBookDTO);

        // Call the method
        List<RecipeBookDTO> result = recipeBookService.findAllByUser();

        // Assertions
        assertEquals(1, result.size());
        assertEquals(mockRecipeBookDTO.getId(), result.get(0).getId());

        // Verify interactions
        verify(recipeBookRepository).findByUserIsCurrentUser();
        verify(recipeBookMapper).toDtoWithRecipeImages(mockRecipeBook);
    }
}
