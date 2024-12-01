package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.RecipeBook}.
 */
@Service
@Transactional
public class RecipeBookService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeBookService.class);

    private final RecipeBookRepository recipeBookRepository;

    private final RecipeBookMapper recipeBookMapper;
    private final RecipeBookRelationRepository recipeBookRelationRepository;
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    public RecipeBookService(
        RecipeBookRepository recipeBookRepository,
        RecipeBookMapper recipeBookMapper,
        RecipeBookRelationRepository recipeBookRelationRepository,
        RecipeRepository recipeRepository,
        UserService userService
    ) {
        this.recipeBookRepository = recipeBookRepository;
        this.recipeBookMapper = recipeBookMapper;
        this.recipeBookRelationRepository = recipeBookRelationRepository;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    /**
     * Save a recipeBook.
     *
     * @param recipeBookDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeBookDTO save(RecipeBookDTO recipeBookDTO) {
        LOG.debug("Request to save RecipeBook : {}", recipeBookDTO);
        RecipeBook recipeBook = recipeBookMapper.toEntity(recipeBookDTO);
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Not found"));
        recipeBook.setUser(user);
        recipeBook = recipeBookRepository.save(recipeBook);
        return recipeBookMapper.toDto(recipeBook);
    }

    /**
     * Update a recipeBook.
     *
     * @param recipeBookDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeBookDTO update(RecipeBookDTO recipeBookDTO) {
        LOG.debug("Request to update RecipeBook : {}", recipeBookDTO);
        RecipeBook recipeBook = recipeBookMapper.toEntity(recipeBookDTO);
        recipeBook = recipeBookRepository.save(recipeBook);
        return recipeBookMapper.toDto(recipeBook);
    }

    /**
     * Partially update a recipeBook.
     *
     * @param recipeBookDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecipeBookDTO> partialUpdate(RecipeBookDTO recipeBookDTO) {
        LOG.debug("Request to partially update RecipeBook : {}", recipeBookDTO);

        return recipeBookRepository
            .findById(recipeBookDTO.getId())
            .map(existingRecipeBook -> {
                recipeBookMapper.partialUpdate(existingRecipeBook, recipeBookDTO);

                return existingRecipeBook;
            })
            .map(recipeBookRepository::save)
            .map(recipeBookMapper::toDto);
    }

    /**
     * Get all the recipeBooks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(String title, String description, String theme, String tag, Pageable pageable) {
        LOG.debug("Request to get all RecipeBooks");
        Page<RecipeBook> recipeBooks = recipeBookRepository.findAllBySearch(title, description, theme, tag, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(recipeBooks.map(recipeBookMapper::toDtoWithRecipeImages));
    }

    /**
     * Get one recipeBook by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeBookDTO> findOne(Long id) {
        LOG.debug("Request to get RecipeBook : {}", id);
        return recipeBookRepository.findById(id).map(recipeBookMapper::toDto);
    }

    /**
     * Delete the recipeBook by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RecipeBook : {}", id);
        recipeBookRepository.deleteById(id);
    }

    public List<RecipeBookDTO> findAllByRecipeId(long recipeId) {
        return recipeBookRepository
            .findByUserIsCurrentUser()
            .stream()
            .map(recipeBook -> recipeBookMapper.toDtoWithIsRecipeInTheList(recipeBook, recipeId))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<RecipeBookDTO> findAllByUser() {
        return recipeBookRepository
            .findByUserIsCurrentUser()
            .stream()
            .map(recipeBookMapper::toDtoWithRecipeImages)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public String getOwner(Long recipeBookId) {
        return recipeBookRepository.getOwner(recipeBookId);
    }

    //TODO: valid user changing the recipeBook!
    /**
     * Change recipeBook status with recipeBook id.
     *
     * @param recipeBookId the id of the entity.
     */
    public ResponseEntity<RecipeBookDTO> changeStatus(Long recipeBookId) {
        if (recipeBookId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RecipeBook recipeBook = recipeBookRepository.findById(recipeBookId).orElseThrow(() -> new RuntimeException("Not found"));
        recipeBook.setIsPrivate(!recipeBook.getIsPrivate());
        recipeBook = recipeBookRepository.save(recipeBook);
        return ResponseEntity.ok(recipeBookMapper.toDto(recipeBook));
    }
}
