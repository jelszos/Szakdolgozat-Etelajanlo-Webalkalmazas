package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.IngredientAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.Ingredient;
import com.szakdologzat.repiceapp.domain.enumeration.Unit;
import com.szakdologzat.repiceapp.repository.IngredientRepository;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.mapper.IngredientMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IngredientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngredientResourceIT {

    private static final String DEFAULT_NAME = "name";
    private static final String UPDATED_NAME = "updated_name";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final Unit DEFAULT_UNIT = Unit.GRAM;
    private static final Unit UPDATED_UNIT = Unit.MILLILITRES;

    private static final String ENTITY_API_URL = "/api/ingredients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientMockMvc;

    private Ingredient ingredient;

    private Ingredient insertedIngredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createEntity() {
        return new Ingredient().name(DEFAULT_NAME).amount(DEFAULT_AMOUNT).unit(DEFAULT_UNIT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createUpdatedEntity() {
        return new Ingredient().name(UPDATED_NAME).amount(UPDATED_AMOUNT).unit(UPDATED_UNIT);
    }

    @BeforeEach
    public void initTest() {
        ingredient = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedIngredient != null) {
            ingredientRepository.delete(insertedIngredient);
            insertedIngredient = null;
        }
    }

    @Test
    @Transactional
    void createIngredient() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);
        var returnedIngredientDTO = om.readValue(
            restIngredientMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingredientDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IngredientDTO.class
        );

        // Validate the Ingredient in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIngredient = ingredientMapper.toEntity(returnedIngredientDTO);
        assertIngredientUpdatableFieldsEquals(returnedIngredient, getPersistedIngredient(returnedIngredient));

        insertedIngredient = returnedIngredient;
    }

    @Test
    @Transactional
    void createIngredientWithExistingId() throws Exception {
        // Create the Ingredient with an existing ID
        ingredient.setId(1L);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngredients() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList
        restIngredientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    void getIngredient() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        // Get the ingredient
        restIngredientMockMvc
            .perform(get(ENTITY_API_URL_ID, ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIngredient() throws Exception {
        // Get the ingredient
        restIngredientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngredient() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingredient
        Ingredient updatedIngredient = ingredientRepository.findById(ingredient.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIngredient are not directly saved in db
        em.detach(updatedIngredient);
        updatedIngredient.name(UPDATED_NAME).amount(UPDATED_AMOUNT).unit(UPDATED_UNIT);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(updatedIngredient);

        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIngredientToMatchAllProperties(updatedIngredient);
    }

    @Test
    @Transactional
    void putNonExistingIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingredientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngredientWithPatch() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingredient using partial update
        Ingredient partialUpdatedIngredient = new Ingredient();
        partialUpdatedIngredient.setId(ingredient.getId());

        partialUpdatedIngredient.amount(UPDATED_AMOUNT).unit(UPDATED_UNIT);

        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredient.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngredient))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngredientUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIngredient, ingredient),
            getPersistedIngredient(ingredient)
        );
    }

    @Test
    @Transactional
    void fullUpdateIngredientWithPatch() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingredient using partial update
        Ingredient partialUpdatedIngredient = new Ingredient();
        partialUpdatedIngredient.setId(ingredient.getId());

        partialUpdatedIngredient.name(UPDATED_NAME).amount(UPDATED_AMOUNT).unit(UPDATED_UNIT);

        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredient.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngredient))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngredientUpdatableFieldsEquals(partialUpdatedIngredient, getPersistedIngredient(partialUpdatedIngredient));
    }

    @Test
    @Transactional
    void patchNonExistingIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngredient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingredient.setId(longCount.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ingredientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingredient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void getAllUniqueIngredients() throws Exception {
        Ingredient ingredient2 = new Ingredient().name("Sugar").amount(50).unit(Unit.GRAM);
        Ingredient ingredient3 = new Ingredient().name("Sugar").amount(100).unit(Unit.GRAM);
        ingredientRepository.saveAndFlush(ingredient);
        ingredientRepository.saveAndFlush(ingredient2);
        ingredientRepository.saveAndFlush(ingredient3);

        restIngredientMockMvc
            .perform(get("/api/ingredients/unique_ingredients"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasItem("name")))
            .andExpect(jsonPath("$").value(hasItem("Sugar")))
            .andExpect(jsonPath("$").value(hasSize(2)));

        ingredientRepository.delete(ingredient);
        ingredientRepository.delete(ingredient2);
        ingredientRepository.delete(ingredient3);
    }

    @Test
    @Transactional
    void deleteIngredient() throws Exception {
        // Initialize the database
        insertedIngredient = ingredientRepository.saveAndFlush(ingredient);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ingredient
        restIngredientMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingredient.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ingredientRepository.count();
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

    protected Ingredient getPersistedIngredient(Ingredient ingredient) {
        return ingredientRepository.findById(ingredient.getId()).orElseThrow();
    }

    protected void assertPersistedIngredientToMatchAllProperties(Ingredient expectedIngredient) {
        assertIngredientAllPropertiesEquals(expectedIngredient, getPersistedIngredient(expectedIngredient));
    }

    protected void assertPersistedIngredientToMatchUpdatableProperties(Ingredient expectedIngredient) {
        assertIngredientAllUpdatablePropertiesEquals(expectedIngredient, getPersistedIngredient(expectedIngredient));
    }
}
