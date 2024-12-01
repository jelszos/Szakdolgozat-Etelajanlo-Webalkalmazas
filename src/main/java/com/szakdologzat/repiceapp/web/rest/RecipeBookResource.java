package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.service.RecipeBookRelationService;
import com.szakdologzat.repiceapp.service.RecipeBookService;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.RecipeBook}.
 */
@RestController
@RequestMapping("/api/recipe-books")
public class RecipeBookResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeBookResource.class);

    private static final String ENTITY_NAME = "recipeBook";
    private final RecipeBookRelationService recipeBookRelationService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeBookService recipeBookService;

    private final RecipeBookRepository recipeBookRepository;

    public RecipeBookResource(
        RecipeBookService recipeBookService,
        RecipeBookRepository recipeBookRepository,
        RecipeBookRelationService recipeBookRelationService
    ) {
        this.recipeBookService = recipeBookService;
        this.recipeBookRepository = recipeBookRepository;
        this.recipeBookRelationService = recipeBookRelationService;
    }

    /**
     * {@code POST  /recipe-books} : Create a new recipeBook.
     *
     * @param recipeBookDTO the recipeBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeBookDTO, or with status {@code 400 (Bad Request)} if the recipeBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecipeBookDTO> createRecipeBook(@RequestBody RecipeBookDTO recipeBookDTO) throws URISyntaxException {
        LOG.debug("REST request to save RecipeBook : {}", recipeBookDTO);
        if (recipeBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recipeBookDTO = recipeBookService.save(recipeBookDTO);
        return ResponseEntity.created(new URI("/api/recipe-books/" + recipeBookDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recipeBookDTO.getId().toString()))
            .body(recipeBookDTO);
    }

    /**
     * {@code PUT  /recipe-books/:id} : Updates an existing recipeBook.
     *
     * @param id the id of the recipeBookDTO to save.
     * @param recipeBookDTO the recipeBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeBookDTO,
     * or with status {@code 400 (Bad Request)} if the recipeBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeBookDTO> updateRecipeBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeBookDTO recipeBookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RecipeBook : {}, {}", id, recipeBookDTO);
        if (recipeBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recipeBookDTO = recipeBookService.update(recipeBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeBookDTO.getId().toString()))
            .body(recipeBookDTO);
    }

    /**
     * {@code PATCH  /recipe-books/:id} : Partial updates given fields of an existing recipeBook, field will ignore if it is null
     *
     * @param id the id of the recipeBookDTO to save.
     * @param recipeBookDTO the recipeBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeBookDTO,
     * or with status {@code 400 (Bad Request)} if the recipeBookDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recipeBookDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipeBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecipeBookDTO> partialUpdateRecipeBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeBookDTO recipeBookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RecipeBook partially : {}, {}", id, recipeBookDTO);
        if (recipeBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipeBookDTO> result = recipeBookService.partialUpdate(recipeBookDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeBookDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recipe-books} : get all the recipeBooks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeBooks in body.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllRecipeBooks(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "theme", required = false) String theme,
        @RequestParam(value = "tag", required = false) String tag,
        Pageable pageable
    ) {
        LOG.debug("REST request to get all RecipeBooks");
        return recipeBookService.findAll(title, description, theme, tag, pageable);
    }

    /**
     * {@code GET  /recipe-books/:id} : get the "id" recipeBook.
     *
     * @param id the id of the recipeBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeBookDTO> getRecipeBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RecipeBook : {}", id);
        Optional<RecipeBookDTO> recipeBookDTO = recipeBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeBookDTO);
    }

    /**
     * {@code DELETE  /recipe-books/:id} : delete the "id" recipeBook.
     *
     * @param id the id of the recipeBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RecipeBook : {}", id);
        recipeBookService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/find-in-recipe/{recipeId}")
    public List<RecipeBookDTO> getRecipeBooksByRecipeId(@PathVariable(value = "recipeId", required = false) Long recipeId) {
        return recipeBookService.findAllByRecipeId(recipeId);
    }

    @GetMapping("/my-recipe-books")
    public List<RecipeBookDTO> getRecipeBooksByUser() {
        return recipeBookService.findAllByUser();
    }

    @PostMapping("/{recipeBookId}/{recipeId}")
    public ResponseEntity<?> addOrRemoveRecipe(
        @PathVariable(value = "recipeBookId") Long recipeBookId,
        @PathVariable(value = "recipeId", required = false) Long recipeId
    ) {
        return recipeBookRelationService.addOrRemoveRecipe(recipeBookId, recipeId);
    }

    @PatchMapping("/status/{recipeBookId}")
    public ResponseEntity<RecipeBookDTO> updateStatus(@PathVariable(value = "recipeBookId") Long recipeBookId) {
        return recipeBookService.changeStatus(recipeBookId);
    }

    @GetMapping("/getOwner/{recipeBookId}")
    public String getOwner(@PathVariable(value = "recipeBookId") Long recipeBookId) {
        return recipeBookService.getOwner(recipeBookId);
    }
}
