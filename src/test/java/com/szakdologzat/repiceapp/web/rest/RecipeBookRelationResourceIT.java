package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.RecipeBookRelationAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.repository.RecipeBookRelationRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookRelationMapper;
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
 * Integration tests for the {@link RecipeBookRelationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecipeBookRelationResourceIT {

    private static final String ENTITY_API_URL = "/api/recipe-book-relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecipeBookRelationRepository recipeBookRelationRepository;

    @Autowired
    private RecipeBookRelationMapper recipeBookRelationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeBookRelationMockMvc;

    private RecipeBookRelation recipeBookRelation;

    private RecipeBookRelation insertedRecipeBookRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeBookRelation createEntity() {
        return new RecipeBookRelation();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeBookRelation createUpdatedEntity() {
        return new RecipeBookRelation();
    }

    @BeforeEach
    public void initTest() {
        recipeBookRelation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecipeBookRelation != null) {
            recipeBookRelationRepository.delete(insertedRecipeBookRelation);
            insertedRecipeBookRelation = null;
        }
    }

    @Test
    @Transactional
    void createRecipeBookRelation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);
        var returnedRecipeBookRelationDTO = om.readValue(
            restRecipeBookRelationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(recipeBookRelationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecipeBookRelationDTO.class
        );

        // Validate the RecipeBookRelation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecipeBookRelation = recipeBookRelationMapper.toEntity(returnedRecipeBookRelationDTO);
        assertRecipeBookRelationUpdatableFieldsEquals(
            returnedRecipeBookRelation,
            getPersistedRecipeBookRelation(returnedRecipeBookRelation)
        );

        insertedRecipeBookRelation = returnedRecipeBookRelation;
    }

    @Test
    @Transactional
    void createRecipeBookRelationWithExistingId() throws Exception {
        // Create the RecipeBookRelation with an existing ID
        recipeBookRelation.setId(1L);
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeBookRelationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecipeBookRelations() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        // Get all the recipeBookRelationList
        restRecipeBookRelationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeBookRelation.getId().intValue())));
    }

    @Test
    @Transactional
    void getRecipeBookRelation() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        // Get the recipeBookRelation
        restRecipeBookRelationMockMvc
            .perform(get(ENTITY_API_URL_ID, recipeBookRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeBookRelation.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRecipeBookRelation() throws Exception {
        // Get the recipeBookRelation
        restRecipeBookRelationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecipeBookRelation() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBookRelation
        RecipeBookRelation updatedRecipeBookRelation = recipeBookRelationRepository.findById(recipeBookRelation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecipeBookRelation are not directly saved in db
        em.detach(updatedRecipeBookRelation);
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(updatedRecipeBookRelation);

        restRecipeBookRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeBookRelationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecipeBookRelationToMatchAllProperties(updatedRecipeBookRelation);
    }

    @Test
    @Transactional
    void putNonExistingRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeBookRelationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeBookRelationWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBookRelation using partial update
        RecipeBookRelation partialUpdatedRecipeBookRelation = new RecipeBookRelation();
        partialUpdatedRecipeBookRelation.setId(recipeBookRelation.getId());

        restRecipeBookRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeBookRelation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeBookRelation))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBookRelation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeBookRelationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRecipeBookRelation, recipeBookRelation),
            getPersistedRecipeBookRelation(recipeBookRelation)
        );
    }

    @Test
    @Transactional
    void fullUpdateRecipeBookRelationWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBookRelation using partial update
        RecipeBookRelation partialUpdatedRecipeBookRelation = new RecipeBookRelation();
        partialUpdatedRecipeBookRelation.setId(recipeBookRelation.getId());

        restRecipeBookRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeBookRelation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeBookRelation))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBookRelation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeBookRelationUpdatableFieldsEquals(
            partialUpdatedRecipeBookRelation,
            getPersistedRecipeBookRelation(partialUpdatedRecipeBookRelation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipeBookRelationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipeBookRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBookRelation.setId(longCount.incrementAndGet());

        // Create the RecipeBookRelation
        RecipeBookRelationDTO recipeBookRelationDTO = recipeBookRelationMapper.toDto(recipeBookRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookRelationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeBookRelationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeBookRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipeBookRelation() throws Exception {
        // Initialize the database
        insertedRecipeBookRelation = recipeBookRelationRepository.saveAndFlush(recipeBookRelation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recipeBookRelation
        restRecipeBookRelationMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipeBookRelation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recipeBookRelationRepository.count();
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

    protected RecipeBookRelation getPersistedRecipeBookRelation(RecipeBookRelation recipeBookRelation) {
        return recipeBookRelationRepository.findById(recipeBookRelation.getId()).orElseThrow();
    }

    protected void assertPersistedRecipeBookRelationToMatchAllProperties(RecipeBookRelation expectedRecipeBookRelation) {
        assertRecipeBookRelationAllPropertiesEquals(expectedRecipeBookRelation, getPersistedRecipeBookRelation(expectedRecipeBookRelation));
    }

    protected void assertPersistedRecipeBookRelationToMatchUpdatableProperties(RecipeBookRelation expectedRecipeBookRelation) {
        assertRecipeBookRelationAllUpdatablePropertiesEquals(
            expectedRecipeBookRelation,
            getPersistedRecipeBookRelation(expectedRecipeBookRelation)
        );
    }
}
