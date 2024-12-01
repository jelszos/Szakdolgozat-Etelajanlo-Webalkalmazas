package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.FavoriteRelation;
import com.szakdologzat.repiceapp.repository.FavoriteRelationRepository;
import com.szakdologzat.repiceapp.service.dto.FavoriteRelationDTO;
import com.szakdologzat.repiceapp.service.mapper.FavoriteRelationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.FavoriteRelation}.
 */
@Service
@Transactional
public class FavoriteRelationService {

    private static final Logger LOG = LoggerFactory.getLogger(FavoriteRelationService.class);

    private final FavoriteRelationRepository favoriteRelationRepository;

    private final FavoriteRelationMapper favoriteRelationMapper;

    public FavoriteRelationService(FavoriteRelationRepository favoriteRelationRepository, FavoriteRelationMapper favoriteRelationMapper) {
        this.favoriteRelationRepository = favoriteRelationRepository;
        this.favoriteRelationMapper = favoriteRelationMapper;
    }

    /**
     * Save a favoriteRelation.
     *
     * @param favoriteRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public FavoriteRelationDTO save(FavoriteRelationDTO favoriteRelationDTO) {
        LOG.debug("Request to save FavoriteRelation : {}", favoriteRelationDTO);
        FavoriteRelation favoriteRelation = favoriteRelationMapper.toEntity(favoriteRelationDTO);
        favoriteRelation = favoriteRelationRepository.save(favoriteRelation);
        return favoriteRelationMapper.toDto(favoriteRelation);
    }

    /**
     * Update a favoriteRelation.
     *
     * @param favoriteRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public FavoriteRelationDTO update(FavoriteRelationDTO favoriteRelationDTO) {
        LOG.debug("Request to update FavoriteRelation : {}", favoriteRelationDTO);
        FavoriteRelation favoriteRelation = favoriteRelationMapper.toEntity(favoriteRelationDTO);
        favoriteRelation = favoriteRelationRepository.save(favoriteRelation);
        return favoriteRelationMapper.toDto(favoriteRelation);
    }

    /**
     * Partially update a favoriteRelation.
     *
     * @param favoriteRelationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FavoriteRelationDTO> partialUpdate(FavoriteRelationDTO favoriteRelationDTO) {
        LOG.debug("Request to partially update FavoriteRelation : {}", favoriteRelationDTO);

        return favoriteRelationRepository
            .findById(favoriteRelationDTO.getId())
            .map(existingFavoriteRelation -> {
                favoriteRelationMapper.partialUpdate(existingFavoriteRelation, favoriteRelationDTO);

                return existingFavoriteRelation;
            })
            .map(favoriteRelationRepository::save)
            .map(favoriteRelationMapper::toDto);
    }

    /**
     * Get all the favoriteRelations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FavoriteRelationDTO> findAll() {
        LOG.debug("Request to get all FavoriteRelations");
        return favoriteRelationRepository
            .findAll()
            .stream()
            .map(favoriteRelationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one favoriteRelation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FavoriteRelationDTO> findOne(Long id) {
        LOG.debug("Request to get FavoriteRelation : {}", id);
        return favoriteRelationRepository.findById(id).map(favoriteRelationMapper::toDto);
    }

    /**
     * Delete the favoriteRelation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete FavoriteRelation : {}", id);
        favoriteRelationRepository.deleteById(id);
    }

    public long getFavoriteCount(Long recipeId) {
        /// LOG.debug("Request to delete FavoriteRelation : {}", recipeId);
        return favoriteRelationRepository.getFavoriteCount(recipeId);
    }
}
