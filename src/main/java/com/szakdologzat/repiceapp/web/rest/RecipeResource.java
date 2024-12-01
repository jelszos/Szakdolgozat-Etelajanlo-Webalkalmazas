package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.repository.IngredientRepository;
import com.szakdologzat.repiceapp.repository.InstructionRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.RecipeService;
import com.szakdologzat.repiceapp.service.UserService;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import com.szakdologzat.repiceapp.web.rest.errors.BadRequestAlertException;
import io.micrometer.common.lang.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.Recipe}.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeResource.class);

    private static final String ENTITY_NAME = "recipe";
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeRepository recipeRepository;

    public RecipeResource(RecipeRepository recipeRepository, RecipeService recipeService, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
    }

    /**
     * {@code POST  /recipes} : Create a new recipe.
     *
     * @param recipeDTO the recipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeDTO, or with status {@code 400 (Bad Request)} if the recipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recipe : {}", recipeDTO);
        if (recipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String ingredientNames = recipeDTO
            .getInstructions()
            .stream()
            .flatMap(instruction -> instruction.getIngredients().stream())
            .map(IngredientDTO::getName)
            .collect(Collectors.joining(","));
        recipeDTO.setIngredientNames(ingredientNames);

        recipeDTO.setIngredientNames(ingredientNames);
        RecipeDTO result = recipeService.save(recipeDTO);

        return ResponseEntity.created(new URI("/api/recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipes/:id} : Updates an existing recipe.
     *
     * @param id the id of the recipeDTO to save.
     * @param recipeDTO the recipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeDTO,
     * or with status {@code 400 (Bad Request)} if the recipeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeDTO recipeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recipe : {}, {}", id, recipeDTO);
        if (recipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recipeDTO = recipeService.update(recipeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeDTO.getId().toString()))
            .body(recipeDTO);
    }

    /**
     * {@code PATCH  /recipes/:id} : Partial updates given fields of an existing recipe, field will ignore if it is null
     *
     * @param id the id of the recipeDTO to save.
     * @param recipeDTO the recipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeDTO,
     * or with status {@code 400 (Bad Request)} if the recipeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recipeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecipeDTO> partialUpdateRecipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecipeDTO recipeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recipe partially : {}, {}", id, recipeDTO);
        if (recipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipeDTO> result = recipeService.partialUpdate(recipeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recipes} : get all the recipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipes in body.
     */
    @GetMapping("")
    public List<RecipeDTO> getAllRecipes() {
        LOG.debug("REST request to get all Recipes");
        return recipeService.findAll();
    }

    @GetMapping("queries/highest_favorite_count")
    public List<RecipeDTO> getHighestFavoriteCountRecipes() {
        LOG.debug("REST request to get recipes with the highest favorite counts");

        // Fetch all recipes
        List<RecipeDTO> list = recipeService.findHighestFavoriteCounts();
        return recipeService.findHighestFavoriteCounts();
    }

    @GetMapping("queries/latest")
    public List<RecipeDTO> getLatestRecipes() {
        LOG.debug("REST request to get recipes with the latest date");

        // Fetch all recipes
        return recipeService.findLatestRecipes();
    }

    @GetMapping("queries/same_food_category/{food_category}")
    public List<RecipeDTO> getSameFoodCategoryRecipes(@PathVariable(value = "food_category") final FoodCategory foodCategory) {
        LOG.debug("REST request to get recipes with the same food category");

        // Fetch all recipes from same food category
        return recipeService.findSameFoodCategoryRecipes(foodCategory);
    }

    /**
     * {@code GET  /recipes/:id} : get the "id" recipe.
     *
     * @param id the id of the recipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recipe : {}", id);
        Optional<RecipeDTO> recipeDTO = recipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeDTO);
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<RatingDTO>> getRecipeRatings(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recipe : {}", id);
        Optional<List<RatingDTO>> ratingDTO = recipeService.findRecipeRatings(id);
        return ResponseUtil.wrapOrNotFound(ratingDTO);
    }

    /**
     * {@code DELETE  /recipes/:id} : delete the "id" recipe.
     *
     * @param id the id of the recipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recipe : {}", id);
        return recipeService.delete(id);
    }

    @GetMapping("/search")
    public Page<RecipeDTO> search(
        @RequestParam(required = false) String recipeTitle,
        @RequestParam(required = false) List<String> foodCategories,
        @RequestParam(required = false) List<String> foodTypes,
        @RequestParam(required = false) List<String> ingredientNames,
        @Nullable Pageable pageable
    ) {
        Page<Recipe> resultList = recipeService.searchRecipes(recipeTitle, foodCategories, foodTypes, ingredientNames, pageable);
        List<RecipeDTO> dtoList = resultList.stream().map(recipeMapper::toDto).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, resultList.getTotalElements());
    }
}
