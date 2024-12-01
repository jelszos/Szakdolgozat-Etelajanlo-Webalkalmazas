package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.domain.FavoriteRelation;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.FavoriteRelationRepository;
import com.szakdologzat.repiceapp.service.FavoriteRelationService;
import com.szakdologzat.repiceapp.service.UserService;
import com.szakdologzat.repiceapp.service.dto.FavoriteRelationDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import com.szakdologzat.repiceapp.service.mapper.FavoriteRelationMapper;
import com.szakdologzat.repiceapp.service.mapper.UserMapper;
import com.szakdologzat.repiceapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.FavoriteRelation}.
 */
@RestController
@RequestMapping("/api/favorite-relations")
public class FavoriteRelationResource {

    private static final Logger LOG = LoggerFactory.getLogger(FavoriteRelationResource.class);

    private static final String ENTITY_NAME = "favoriteRelation";
    private final UserService userService;
    private final FavoriteRelationMapper favoriteRelationMapper;
    private final UserMapper userMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavoriteRelationService favoriteRelationService;

    private final FavoriteRelationRepository favoriteRelationRepository;

    public FavoriteRelationResource(
        FavoriteRelationService favoriteRelationService,
        FavoriteRelationRepository favoriteRelationRepository,
        UserService userService,
        FavoriteRelationMapper favoriteRelationMapper,
        UserMapper userMapper
    ) {
        this.favoriteRelationService = favoriteRelationService;
        this.favoriteRelationRepository = favoriteRelationRepository;
        this.userService = userService;
        this.favoriteRelationMapper = favoriteRelationMapper;
        this.userMapper = userMapper;
    }

    /**
     * {@code POST  /favorite-relations} : Create a new favoriteRelation.
     *
     * @param favoriteRelationDTO the favoriteRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteRelationDTO, or with status {@code 400 (Bad Request)} if the favoriteRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<?> createFavoriteRelation(@RequestBody FavoriteRelationDTO favoriteRelationDTO) throws URISyntaxException {
        LOG.debug("REST request to save FavoriteRelation : {}", favoriteRelationDTO);
        // Lekérjük a bejelentkezett felhasználót
        User loggedInUser = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("User not found"));

        // Megkeressük, hogy már létezik-e a kapcsolat az adott userId és recipeId alapján
        return favoriteRelationRepository
            .findByUserIdAndRecipeId(loggedInUser.getId(), favoriteRelationDTO.getRecipe().getId())
            .map(existingRelation -> {
                // Ha létezik, töröljük, majd visszaadjuk a törölt objektumot DTO formájában
                favoriteRelationRepository.deleteById(existingRelation.getId());
                FavoriteRelationDTO deletedDto = favoriteRelationMapper.toDto(existingRelation); // Törölt objektum DTO
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedDto); // Visszaadjuk DTO-val a választ
            })
            .orElseGet(() -> {
                // Ha nem létezik, létrehozzuk
                favoriteRelationDTO.setUser(userMapper.userToUserDTO(loggedInUser));
                FavoriteRelationDTO result = favoriteRelationService.save(favoriteRelationDTO);

                // URI létrehozása és válasz
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            });
    }

    /**
     * {@code PUT  /favorite-relations/:id} : Updates an existing favoriteRelation.
     *
     * @param id the id of the favoriteRelationDTO to save.
     * @param favoriteRelationDTO the favoriteRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteRelationDTO,
     * or with status {@code 400 (Bad Request)} if the favoriteRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favoriteRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FavoriteRelationDTO> updateFavoriteRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FavoriteRelationDTO favoriteRelationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FavoriteRelation : {}, {}", id, favoriteRelationDTO);
        if (favoriteRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteRelationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        favoriteRelationDTO = favoriteRelationService.update(favoriteRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteRelationDTO.getId().toString()))
            .body(favoriteRelationDTO);
    }

    /**
     * {@code PATCH  /favorite-relations/:id} : Partial updates given fields of an existing favoriteRelation, field will ignore if it is null
     *
     * @param id the id of the favoriteRelationDTO to save.
     * @param favoriteRelationDTO the favoriteRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteRelationDTO,
     * or with status {@code 400 (Bad Request)} if the favoriteRelationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the favoriteRelationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the favoriteRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FavoriteRelationDTO> partialUpdateFavoriteRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FavoriteRelationDTO favoriteRelationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FavoriteRelation partially : {}, {}", id, favoriteRelationDTO);
        if (favoriteRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteRelationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FavoriteRelationDTO> result = favoriteRelationService.partialUpdate(favoriteRelationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteRelationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /favorite-relations} : get all the favoriteRelations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favoriteRelations in body.
     */
    @GetMapping("")
    public List<FavoriteRelationDTO> getAllFavoriteRelations() {
        LOG.debug("REST request to get all FavoriteRelations");
        return favoriteRelationService.findAll();
    }

    /**
     * {@code GET  /favorite-relations/:id} : get the "id" favoriteRelation.
     *
     * @param id the id of the favoriteRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favoriteRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FavoriteRelationDTO> getFavoriteRelation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FavoriteRelation : {}", id);
        Optional<FavoriteRelationDTO> favoriteRelationDTO = favoriteRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favoriteRelationDTO);
    }

    /**
     * {@code DELETE  /favorite-relations/:id} : delete the "id" favoriteRelation.
     *
     * @param id the id of the favoriteRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteRelation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FavoriteRelation : {}", id);
        favoriteRelationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
