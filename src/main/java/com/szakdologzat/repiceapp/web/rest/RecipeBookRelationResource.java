package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.service.RecipeBookRelationService;
import com.szakdologzat.repiceapp.service.RecipeBookService;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
import com.szakdologzat.repiceapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.RecipeBookRelation}.
 */
@RestController
@RequestMapping("/api/recipe-book-relations")
public class RecipeBookRelationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeBookRelationResource.class);

    private static final String ENTITY_NAME = "recipeBookRelation";
    private final RecipeBookService recipeBookService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeBookRelationService recipeBookRelationService;

    private final RecipeBookRelationRepository recipeBookRelationRepository;

    public RecipeBookRelationResource(
        RecipeBookRelationService recipeBookRelationService,
        RecipeBookRelationRepository recipeBookRelationRepository,
        RecipeBookService recipeBookService
    ) {
        this.recipeBookRelationService = recipeBookRelationService;
        this.recipeBookRelationRepository = recipeBookRelationRepository;
        this.recipeBookService = recipeBookService;
    }

    /**
     * {@code POST  /recipe-book-relations} : Create a new recipeBookRelation.
     *
     * @param recipeBookRelationDTO the recipeBookRelationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeBookRelationDTO, or with status {@code 400 (Bad Request)} if the recipeBookRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecipeBookRelationDTO> createRecipeBookRelation(@RequestBody RecipeBookRelationDTO recipeBookRelationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save RecipeBookRelation : {}", recipeBookRelationDTO);
        if (recipeBookRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeBookRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recipeBookRelationDTO = recipeBookRelationService.save(recipeBookRelationDTO);
        return ResponseEntity.created(new URI("/api/recipe-book-relations/" + recipeBookRelationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recipeBookRelationDTO.getId().toString()))
            .body(recipeBookRelationDTO);
    }

    /**
     * {@code PUT  /recipe-book-relations/:id} : Updates an existing recipeBookRelation.
     *
     * @param id the id of the recipeBookRelationDTO to save.
     * @param recipeBookRelationDTO the recipeBookRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeBookRelationDTO,
     * or with status {@code 400 (Bad Request)} if the recipeBookRelationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeBookRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeBookRelationDTO> updateRecipeBookRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeBookRelationDTO recipeBookRelationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RecipeBookRelation : {}, {}", id, recipeBookRelationDTO);
        if (recipeBookRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeBookRelationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeBookRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recipeBookRelationDTO = recipeBookRelationService.update(recipeBookRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeBookRelationDTO.getId().toString()))
            .body(recipeBookRelationDTO);
    }

    /**
     * {@code PATCH  /recipe-book-relations/:id} : Partial updates given fields of an existing recipeBookRelation, field will ignore if it is null
     *
     * @param id the id of the recipeBookRelationDTO to save.
     * @param recipeBookRelationDTO the recipeBookRelationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeBookRelationDTO,
     * or with status {@code 400 (Bad Request)} if the recipeBookRelationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recipeBookRelationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipeBookRelationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecipeBookRelationDTO> partialUpdateRecipeBookRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeBookRelationDTO recipeBookRelationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RecipeBookRelation partially : {}, {}", id, recipeBookRelationDTO);
        if (recipeBookRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeBookRelationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeBookRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipeBookRelationDTO> result = recipeBookRelationService.partialUpdate(recipeBookRelationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeBookRelationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recipe-book-relations} : get all the recipeBookRelations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeBookRelations in body.
     */
    @GetMapping("")
    public List<RecipeBookRelationDTO> getAllRecipeBookRelations() {
        LOG.debug("REST request to get all RecipeBookRelations");
        return recipeBookRelationService.findAll();
    }

    /**
     * {@code GET  /recipe-book-relations/:id} : get the "id" recipeBookRelation.
     *
     * @param id the id of the recipeBookRelationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeBookRelationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeBookRelationDTO> getRecipeBookRelation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RecipeBookRelation : {}", id);
        Optional<RecipeBookRelationDTO> recipeBookRelationDTO = recipeBookRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeBookRelationDTO);
    }

    /**
     * {@code DELETE  /recipe-book-relations/:id} : delete the "id" recipeBookRelation.
     *
     * @param id the id of the recipeBookRelationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeBookRelation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RecipeBookRelation : {}", id);
        recipeBookRelationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/all-by-recipe-book-id/{recipeBookId}")
    public ResponseEntity<?> getAllByRecipeBookId(@PathVariable Long recipeBookId) {
        return recipeBookRelationService.getRecipesByRecipeBookId(recipeBookId);
    }

    @DeleteMapping("/delete/{recipeId}/{recipeBookId}")
    public ResponseEntity<String> deleteRecipeFromRecipeBook(@PathVariable Long recipeId, @PathVariable Long recipeBookId) {
        return recipeBookRelationService.deleteRecipeFromRecipeBook(recipeId, recipeBookId);
    }
}
