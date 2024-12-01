package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.Ingredient;
import com.szakdologzat.repiceapp.repository.IngredientRepository;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.mapper.IngredientMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.Ingredient}.
 */
@Service
@Transactional
public class IngredientService {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save.
     * @return the persisted entity.
     */
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        LOG.debug("Request to save Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
    }

    /**
     * Update a ingredient.
     *
     * @param ingredientDTO the entity to save.
     * @return the persisted entity.
     */
    public IngredientDTO update(IngredientDTO ingredientDTO) {
        LOG.debug("Request to update Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
    }

    /**
     * Partially update a ingredient.
     *
     * @param ingredientDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IngredientDTO> partialUpdate(IngredientDTO ingredientDTO) {
        LOG.debug("Request to partially update Ingredient : {}", ingredientDTO);

        return ingredientRepository
            .findById(ingredientDTO.getId())
            .map(existingIngredient -> {
                ingredientMapper.partialUpdate(existingIngredient, ingredientDTO);

                return existingIngredient;
            })
            .map(ingredientRepository::save)
            .map(ingredientMapper::toDto);
    }

    /**
     * Get all the ingredients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() {
        LOG.debug("Request to get all Ingredients");
        return ingredientRepository.findAll().stream().map(ingredientMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ingredient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IngredientDTO> findOne(Long id) {
        LOG.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id).map(ingredientMapper::toDto);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.deleteById(id);
    }

    public Set<String> findUniqueIngredients() {
        return ingredientRepository.findAllUniqueIngredients().stream().map(Ingredient::getName).collect(Collectors.toSet());
    }
}
