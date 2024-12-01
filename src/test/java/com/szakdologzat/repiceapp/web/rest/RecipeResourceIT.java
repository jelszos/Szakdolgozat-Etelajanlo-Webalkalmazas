package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.RecipeAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.domain.enumeration.FoodType;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
class RecipeResourceIT {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_TITLE = "title";
    private static final String UPDATED_TITLE = "updated_title";

    private static final String DEFAULT_DESCRIPTION = "description";
    private static final String UPDATED_DESCRIPTION = "updated_description";

    private static final String DEFAULT_INGREDIENT_NAMES = "ingredient_names";
    private static final String UPDATED_INGREDIENT_NAMES = "updated_ingredient_names";

    private static final FoodCategory DEFAULT_FOOD_CATEGORY = FoodCategory.CHINESE;
    private static final FoodCategory UPDATED_FOOD_CATEGORY = FoodCategory.ITALIAN;

    private static final FoodType DEFAULT_FOOD_TYPE = FoodType.APPETIZER;
    private static final FoodType UPDATED_FOOD_TYPE = FoodType.DESSERT;

    private static final String DEFAULT_IMAGE_URL = "image_url";
    private static final String UPDATED_IMAGE_URL = "update_image_url";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/recipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeMockMvc;

    private Recipe recipe;
    private Recipe updatedRecipe;

    private Recipe insertedRecipe;
    private Recipe insertedRecipeTwo;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createEntity() {
        User user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin("admin");

        return new Recipe()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .ingredientNames(DEFAULT_INGREDIENT_NAMES)
            .foodCategory(DEFAULT_FOOD_CATEGORY)
            .foodType(DEFAULT_FOOD_TYPE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .createdDate(DEFAULT_CREATED_DATE)
            .user(user);
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createUpdatedEntity() {
        User user = new User();
        user.setId(1L);
        user.setLogin("admin");
        return new Recipe()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .ingredientNames(UPDATED_INGREDIENT_NAMES)
            .foodCategory(UPDATED_FOOD_CATEGORY)
            .foodType(UPDATED_FOOD_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .user(user);
    }

    @BeforeEach
    public void initTest() {
        recipe = createEntity();
        recipe.setCreatedDate(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS));
        updatedRecipe = createUpdatedEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecipe != null) {
            recipeRepository.delete(insertedRecipe);
            insertedRecipe = null;
        }
        if (insertedRecipeTwo != null) {
            recipeRepository.delete(insertedRecipeTwo);
            insertedRecipeTwo = null;
        }
    }

    @Test
    @Transactional
    void createRecipe() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        var returnedRecipeDTO = om.readValue(
            restRecipeMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecipeDTO.class
        );

        // Validate the Recipe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecipe = recipeMapper.toEntity(returnedRecipeDTO);
        assertRecipeUpdatableFieldsEquals(returnedRecipe, getPersistedRecipe(returnedRecipe));

        insertedRecipe = returnedRecipe;
    }

    @Test
    @Transactional
    void createRecipeWithExistingId() throws Exception {
        // Create the Recipe with an existing ID
        recipe.setId(1L);
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecipes() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);
        insertedRecipeTwo = recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ingredientNames").value(hasItem(DEFAULT_INGREDIENT_NAMES)))
            .andExpect(jsonPath("$.[*].foodCategory").value(hasItem(DEFAULT_FOOD_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL_ID, recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.ingredientNames").value(DEFAULT_INGREDIENT_NAMES))
            .andExpect(jsonPath("$.foodCategory").value(DEFAULT_FOOD_CATEGORY.toString()))
            .andExpect(jsonPath("$.foodType").value(DEFAULT_FOOD_TYPE.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).orElseThrow();
        em.detach(updatedRecipe);
        updatedRecipe
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .ingredientNames(UPDATED_INGREDIENT_NAMES)
            .foodCategory(UPDATED_FOOD_CATEGORY)
            .foodType(UPDATED_FOOD_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE);
        RecipeDTO recipeDTO = recipeMapper.toDto(updatedRecipe);

        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecipeToMatchAllProperties(updatedRecipe);
    }

    @Test
    @Transactional
    void putNonExistingRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe.title(UPDATED_TITLE).imageUrl(UPDATED_IMAGE_URL);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRecipe, recipe), getPersistedRecipe(recipe));
    }

    @Test
    @Transactional
    void fullUpdateRecipeWithPatch() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipe using partial update
        Recipe partialUpdatedRecipe = new Recipe();
        partialUpdatedRecipe.setId(recipe.getId());

        partialUpdatedRecipe
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .ingredientNames(UPDATED_INGREDIENT_NAMES)
            .foodCategory(UPDATED_FOOD_CATEGORY)
            .foodType(UPDATED_FOOD_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE);

        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipe.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipe))
            )
            .andExpect(status().isOk());

        // Validate the Recipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeUpdatableFieldsEquals(partialUpdatedRecipe, getPersistedRecipe(partialUpdatedRecipe));
    }

    @Test
    @Transactional
    void patchNonExistingRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipe.setId(longCount.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recipeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @PreAuthorize("authenticated")
    void deleteRecipe() throws Exception {
        // Initialize the database
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recipe
        restRecipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipe.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    void getHighestFavoriteCountRecipes() throws Exception {
        insertedRecipe = recipeRepository.saveAndFlush(recipe);
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "/queries/highest_favorite_count"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].foodCategory").value(hasItem(DEFAULT_FOOD_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));
    }

    @Test
    @Transactional
    void getLatestRecipes() throws Exception {
        insertedRecipe = recipeRepository.saveAndFlush(recipe);
        insertedRecipeTwo = recipeRepository.saveAndFlush(updatedRecipe);
        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "/queries/latest"))
            .andExpect(status().isOk())
            .andDo(result -> {
                String responseBody = result.getResponse().getContentAsString();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                String date0 = jsonNode.get(0).get("createdDate").asText();
                String date1 = jsonNode.get(1).get("createdDate").asText();

                // Assert that date0 is later than date1
                assertTrue(Instant.parse(date0).isAfter(Instant.parse(date1)), "Expected createdDate of index 0 to be later than index 1");
            })
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].foodCategory").value(hasItem(DEFAULT_FOOD_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));
    }

    @Test
    @Transactional
    void getSameFoodCategoryRecipes() throws Exception {
        String foodCategory = "CHINESE";
        insertedRecipe = recipeRepository.saveAndFlush(recipe);
        insertedRecipeTwo = recipeRepository.saveAndFlush(updatedRecipe);

        restRecipeMockMvc
            .perform(get(ENTITY_API_URL + "/queries/same_food_category/{food_category}", foodCategory))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*]", hasSize(1)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].foodCategory").value(hasItem(DEFAULT_FOOD_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));

        recipeRepository.delete(insertedRecipe);
        recipeRepository.delete(insertedRecipeTwo);
    }

    @Test
    @Transactional
    void getRecipeRatings() throws Exception {
        Long recipeId = 1L;

        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID + "/ratings", recipeId)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void searchRecipes() throws Exception {
        insertedRecipe = recipeRepository.saveAndFlush(recipe);

        restRecipeMockMvc
            .perform(
                get(ENTITY_API_URL + "/search")
                    .param("recipeTitle", DEFAULT_TITLE)
                    .param("foodCategories", DEFAULT_FOOD_CATEGORY.toString())
                    .param("foodTypes", DEFAULT_FOOD_TYPE.toString())
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.content[*].foodCategory").value(hasItem(DEFAULT_FOOD_CATEGORY.toString())))
            .andExpect(jsonPath("$.content[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));

        // Clean up
        recipeRepository.delete(insertedRecipe);
    }

    protected long getRepositoryCount() {
        return recipeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Recipe getPersistedRecipe(Recipe recipe) {
        return recipeRepository.findById(recipe.getId()).orElseThrow();
    }

    protected void assertPersistedRecipeToMatchAllProperties(Recipe expectedRecipe) {
        assertRecipeAllPropertiesEquals(expectedRecipe, getPersistedRecipe(expectedRecipe));
    }

    protected void assertPersistedRecipeToMatchUpdatableProperties(Recipe expectedRecipe) {
        assertRecipeAllUpdatablePropertiesEquals(expectedRecipe, getPersistedRecipe(expectedRecipe));
    }
}
