package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RatingRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.service.RatingService;
import com.szakdologzat.repiceapp.service.UserService;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.mapper.RatingMapper;
import com.szakdologzat.repiceapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.Rating}.
 */
@RestController
@RequestMapping("/api/ratings")
public class RatingResource {

    private static final Logger LOG = LoggerFactory.getLogger(RatingResource.class);

    private static final String ENTITY_NAME = "rating";
    private final RecipeRepository recipeRepository;
    private final RatingMapper ratingMapper;
    private final UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingService ratingService;

    private final RatingRepository ratingRepository;

    public RatingResource(
        RatingService ratingService,
        RatingRepository ratingRepository,
        RecipeRepository recipeRepository,
        RatingMapper ratingMapper,
        UserService userService
    ) {
        this.ratingService = ratingService;
        this.ratingRepository = ratingRepository;
        this.recipeRepository = recipeRepository;
        this.ratingMapper = ratingMapper;
        this.userService = userService;
    }

    /**
     * {@code POST  /ratings} : Create a new rating.
     *
     * @param ratingDTO the ratingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingDTO, or with status {@code 400 (Bad Request)} if the rating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("{recipeId}")
    public ResponseEntity<RatingDTO> createRatingForRecipe(@PathVariable Long recipeId, @RequestBody RatingDTO ratingDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Rating for Recipe: {}", ratingDTO);
        if (ratingDTO.getId() != null || recipeId == null) {
            throw new BadRequestAlertException("A new rating cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Rating rating = ratingMapper.toEntity(ratingDTO);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RuntimeException::new);

        rating.setRecipe(recipe);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        rating.setUser(user);
        rating = ratingRepository.save(rating);

        return ResponseEntity.created(new URI("/api/ratings/" + rating.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rating.getId().toString()))
            .body(ratingMapper.toDto(rating));
    }

    @PostMapping("")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) throws URISyntaxException {
        LOG.debug("REST request to save Rating : {}", ratingDTO);
        if (ratingDTO.getId() != null) {
            throw new BadRequestAlertException("A new rating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ratingDTO = ratingService.save(ratingDTO);
        return ResponseEntity.created(new URI("/api/ratings/" + ratingDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ratingDTO.getId().toString()))
            .body(ratingDTO);
    }

    /**
     * {@code PUT  /ratings/:id} : Updates an existing rating.
     *
     * @param id the id of the ratingDTO to save.
     * @param ratingDTO the ratingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingDTO,
     * or with status {@code 400 (Bad Request)} if the ratingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingDTO ratingDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Rating : {}, {}", id, ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ratingDTO = ratingService.update(ratingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingDTO.getId().toString()))
            .body(ratingDTO);
    }

    /**
     * {@code PATCH  /ratings/:id} : Partial updates given fields of an existing rating, field will ignore if it is null
     *
     * @param id the id of the ratingDTO to save.
     * @param ratingDTO the ratingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingDTO,
     * or with status {@code 400 (Bad Request)} if the ratingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ratingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingDTO> partialUpdateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingDTO ratingDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Rating partially : {}, {}", id, ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingDTO> result = ratingService.partialUpdate(ratingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ratings} : get all the ratings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratings in body.
     */
    @GetMapping("")
    public List<RatingDTO> getAllRatings() {
        LOG.debug("REST request to get all Ratings");
        return ratingService.findAll();
    }

    /**
     * {@code GET  /ratings/:id} : get the "id" rating.
     *
     * @param id the id of the ratingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Rating : {}", id);
        Optional<RatingDTO> ratingDTO = ratingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingDTO);
    }

    /**
     * {@code DELETE  /ratings/:id} : delete the "id" rating.
     *
     * @param id the id of the ratingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Rating : {}", id);
        ratingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
