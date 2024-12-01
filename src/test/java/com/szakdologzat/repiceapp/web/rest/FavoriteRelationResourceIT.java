package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.FavoriteRelationAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.FavoriteRelation;
import com.szakdologzat.repiceapp.repository.FavoriteRelationRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.dto.FavoriteRelationDTO;
import com.szakdologzat.repiceapp.service.mapper.FavoriteRelationMapper;
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
 * Integration tests for the {@link FavoriteRelationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FavoriteRelationResourceIT {

    private static final String ENTITY_API_URL = "/api/favorite-relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FavoriteRelationRepository favoriteRelationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRelationMapper favoriteRelationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavoriteRelationMockMvc;

    private FavoriteRelation favoriteRelation;

    private FavoriteRelation insertedFavoriteRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteRelation createEntity() {
        return new FavoriteRelation();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteRelation createUpdatedEntity() {
        return new FavoriteRelation();
    }

    @BeforeEach
    public void initTest() {
        favoriteRelation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFavoriteRelation != null) {
            favoriteRelationRepository.delete(insertedFavoriteRelation);
            insertedFavoriteRelation = null;
        }
    }

    @Test
    @Transactional
    void getAllFavoriteRelations() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        // Get all the favoriteRelationList
        restFavoriteRelationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoriteRelation.getId().intValue())));
    }

    @Test
    @Transactional
    void getFavoriteRelation() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        // Get the favoriteRelation
        restFavoriteRelationMockMvc
            .perform(get(ENTITY_API_URL_ID, favoriteRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoriteRelation.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFavoriteRelation() throws Exception {
        // Get the favoriteRelation
        restFavoriteRelationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFavoriteRelation() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the favoriteRelation
        FavoriteRelation updatedFavoriteRelation = favoriteRelationRepository.findById(favoriteRelation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFavoriteRelation are not directly saved in db
        em.detach(updatedFavoriteRelation);
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(updatedFavoriteRelation);

        restFavoriteRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favoriteRelationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFavoriteRelationToMatchAllProperties(updatedFavoriteRelation);
    }

    @Test
    @Transactional
    void putNonExistingFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favoriteRelationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFavoriteRelationWithPatch() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the favoriteRelation using partial update
        FavoriteRelation partialUpdatedFavoriteRelation = new FavoriteRelation();
        partialUpdatedFavoriteRelation.setId(favoriteRelation.getId());

        restFavoriteRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteRelation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFavoriteRelation))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRelation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFavoriteRelationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFavoriteRelation, favoriteRelation),
            getPersistedFavoriteRelation(favoriteRelation)
        );
    }

    @Test
    @Transactional
    void fullUpdateFavoriteRelationWithPatch() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the favoriteRelation using partial update
        FavoriteRelation partialUpdatedFavoriteRelation = new FavoriteRelation();
        partialUpdatedFavoriteRelation.setId(favoriteRelation.getId());

        restFavoriteRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteRelation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFavoriteRelation))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRelation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFavoriteRelationUpdatableFieldsEquals(
            partialUpdatedFavoriteRelation,
            getPersistedFavoriteRelation(partialUpdatedFavoriteRelation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, favoriteRelationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFavoriteRelation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        favoriteRelation.setId(longCount.incrementAndGet());

        // Create the FavoriteRelation
        FavoriteRelationDTO favoriteRelationDTO = favoriteRelationMapper.toDto(favoriteRelation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRelationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(favoriteRelationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteRelation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFavoriteRelation() throws Exception {
        // Initialize the database
        insertedFavoriteRelation = favoriteRelationRepository.saveAndFlush(favoriteRelation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the favoriteRelation
        restFavoriteRelationMockMvc
            .perform(delete(ENTITY_API_URL_ID, favoriteRelation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return favoriteRelationRepository.count();
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

    protected FavoriteRelation getPersistedFavoriteRelation(FavoriteRelation favoriteRelation) {
        return favoriteRelationRepository.findById(favoriteRelation.getId()).orElseThrow();
    }

    protected void assertPersistedFavoriteRelationToMatchAllProperties(FavoriteRelation expectedFavoriteRelation) {
        assertFavoriteRelationAllPropertiesEquals(expectedFavoriteRelation, getPersistedFavoriteRelation(expectedFavoriteRelation));
    }

    protected void assertPersistedFavoriteRelationToMatchUpdatableProperties(FavoriteRelation expectedFavoriteRelation) {
        assertFavoriteRelationAllUpdatablePropertiesEquals(
            expectedFavoriteRelation,
            getPersistedFavoriteRelation(expectedFavoriteRelation)
        );
    }
}
