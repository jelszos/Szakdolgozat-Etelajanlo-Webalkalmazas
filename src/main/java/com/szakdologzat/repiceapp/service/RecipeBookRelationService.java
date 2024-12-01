package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookRelationMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.RecipeBookRelation}.
 */
@Service
@Transactional
public class RecipeBookRelationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeBookRelationService.class);

    private final RecipeBookRelationRepository recipeBookRelationRepository;

    private final RecipeBookRelationMapper recipeBookRelationMapper;
    private final RecipeMapper recipeMapper;
    private final RecipeBookMapper recipeBookMapper;
    private final RecipeRepository recipeRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final UserService userService;

    public RecipeBookRelationService(
        RecipeBookRelationRepository recipeBookRelationRepository,
        RecipeBookRelationMapper recipeBookRelationMapper,
        RecipeMapper recipeMapper,
        RecipeBookMapper recipeBookMapper,
        RecipeRepository recipeRepository,
        RecipeBookRepository recipeBookRepository,
        UserService userService
    ) {
        this.recipeBookRelationRepository = recipeBookRelationRepository;
        this.recipeBookRelationMapper = recipeBookRelationMapper;
        this.recipeMapper = recipeMapper;
        this.recipeBookMapper = recipeBookMapper;
        this.recipeRepository = recipeRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.userService = userService;
    }

    /**
     * Save a recipeBookRelation.
     *
     * @param recipeBookRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeBookRelationDTO save(RecipeBookRelationDTO recipeBookRelationDTO) {
        LOG.debug("Request to save RecipeBookRelation : {}", recipeBookRelationDTO);
        RecipeBookRelation recipeBookRelation = recipeBookRelationMapper.toEntity(recipeBookRelationDTO);
        recipeBookRelation = recipeBookRelationRepository.save(recipeBookRelation);
        return recipeBookRelationMapper.toDto(recipeBookRelation);
    }

    /**
     * Update a recipeBookRelation.
     *
     * @param recipeBookRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeBookRelationDTO update(RecipeBookRelationDTO recipeBookRelationDTO) {
        LOG.debug("Request to update RecipeBookRelation : {}", recipeBookRelationDTO);
        RecipeBookRelation recipeBookRelation = recipeBookRelationMapper.toEntity(recipeBookRelationDTO);
        recipeBookRelation = recipeBookRelationRepository.save(recipeBookRelation);
        return recipeBookRelationMapper.toDto(recipeBookRelation);
    }

    /**
     * Partially update a recipeBookRelation.
     *
     * @param recipeBookRelationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecipeBookRelationDTO> partialUpdate(RecipeBookRelationDTO recipeBookRelationDTO) {
        LOG.debug("Request to partially update RecipeBookRelation : {}", recipeBookRelationDTO);

        return recipeBookRelationRepository
            .findById(recipeBookRelationDTO.getId())
            .map(existingRecipeBookRelation -> {
                recipeBookRelationMapper.partialUpdate(existingRecipeBookRelation, recipeBookRelationDTO);

                return existingRecipeBookRelation;
            })
            .map(recipeBookRelationRepository::save)
            .map(recipeBookRelationMapper::toDto);
    }

    /**
     * Get all the recipeBookRelations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeBookRelationDTO> findAll() {
        LOG.debug("Request to get all RecipeBookRelations");
        return recipeBookRelationRepository
            .findAll()
            .stream()
            .map(recipeBookRelationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recipeBookRelation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeBookRelationDTO> findOne(Long id) {
        LOG.debug("Request to get RecipeBookRelation : {}", id);
        return recipeBookRelationRepository.findById(id).map(recipeBookRelationMapper::toDto);
    }

    /**
     * Delete the recipeBookRelation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RecipeBookRelation : {}", id);
        recipeBookRelationRepository.deleteById(id);
    }

    public Boolean isRecipeInTheList(long recipeId, long recipeBookId) {
        return recipeBookRelationRepository.isRecipeInRecipeBook(recipeId, recipeBookId);
    }

    public ResponseEntity<?> addOrRemoveRecipe(Long recipeBookId, Long recipeId) {
        // we can validate here the user and recipeBook/recipe id
        if (recipeBookRelationRepository.isRecipeInRecipeBook(recipeId, recipeBookId)) {
            recipeBookRelationRepository.deleteByRecipeAndRecipeBookId(recipeId, recipeBookId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        RecipeBook recipeBook = recipeBookRepository.findById(recipeBookId).orElse(null);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        RecipeBookRelation recipeBookRelation = new RecipeBookRelation();
        recipeBookRelation.setRecipeBook(recipeBook);
        recipeBookRelation.setRecipe(recipe);

        RecipeBookRelation resultRbr = recipeBookRelationRepository.save(recipeBookRelation);
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDtoWithIsRecipeInTheList(resultRbr.getRecipeBook(), recipeId);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBookDTO);
    }

    public ResponseEntity<?> getRecipesByRecipeBookId(Long recipeBookId) {
        //        if (!recipeBookRepository.isOwnByUser()) {
        //            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not allowed to view recipes by recipe book");
        //        }
        return ResponseEntity.status(HttpStatus.OK).body(
            recipeBookRelationRepository
                .findAllByRecipeBookId(recipeBookId)
                .stream()
                .map(RecipeBookRelation::getRecipe)
                .map(recipeMapper::toDto)
                .toList()
        );
    }

    public ResponseEntity<String> deleteRecipeFromRecipeBook(Long recipeId, Long recipeBookId) {
        if (!recipeBookRelationRepository.isUserTheOwner(recipeBookId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You do not have permission to delete this recipe");
        }
        RecipeBookRelation rbr = recipeBookRelationRepository
            .getRbrByRecipeBookIdAndRecipeId(recipeId, recipeBookId)
            .orElseThrow(() -> new RuntimeException("Recipe Book Relation does not exist"));
        recipeBookRelationRepository.delete(rbr);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public List<String> findImagesInRecipeBook(Long recipeBookId) {
        return recipeBookRelationRepository
            .findAllByRecipeBookId(recipeBookId)
            .stream()
            .map(recipeBookRelation -> recipeBookRelation.getRecipe().getImageUrl())
            .limit(4)
            .collect(Collectors.toList());
    }
}
