package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.RatingAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RatingRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.mapper.RatingMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link RatingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingResourceIT {

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final String DEFAULT_TITLE = "title";
    private static final String UPDATED_TITLE = "updated_title";

    private static final String DEFAULT_DESCRIPTION = "description";
    private static final String UPDATED_DESCRIPTION = "updated_description";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RatingMapper ratingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingMockMvc;

    private Rating rating;

    private Rating insertedRating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createEntity() {
        return new Rating()
            .rate(DEFAULT_RATE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .createdDate(DEFAULT_CREATED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createUpdatedEntity() {
        return new Rating()
            .rate(UPDATED_RATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        rating = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRating != null) {
            ratingRepository.delete(insertedRating);
            insertedRating = null;
        }
    }

    @Test
    @Transactional
    void createRating() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);
        var returnedRatingDTO = om.readValue(
            restRatingMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ratingDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RatingDTO.class
        );

        // Validate the Rating in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRating = ratingMapper.toEntity(returnedRatingDTO);
        assertRatingUpdatableFieldsEquals(returnedRating, getPersistedRating(returnedRating));

        insertedRating = returnedRating;
    }

    @Test
    @Transactional
    void createRatingWithExistingId() throws Exception {
        // Create the Rating with an existing ID
        rating.setId(1L);
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void createRatingForRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe = recipeRepository.saveAndFlush(recipe);

        // Create a new RatingDTO
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRate(DEFAULT_RATE);
        ratingDTO.setTitle(DEFAULT_TITLE);
        ratingDTO.setDescription(DEFAULT_DESCRIPTION);
        ratingDTO.setImageUrl(DEFAULT_IMAGE_URL);

        long databaseSizeBeforeCreate = ratingRepository.count();

        var returnedRatingDTO = om.readValue(
            restRatingMockMvc
                .perform(
                    post(ENTITY_API_URL + "/" + recipe.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ratingDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RatingDTO.class
        );

        assertThat(ratingRepository.count()).isEqualTo(databaseSizeBeforeCreate + 1);

        assertThat(returnedRatingDTO.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(returnedRatingDTO.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(returnedRatingDTO.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(returnedRatingDTO.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);

        Rating persistedRating = ratingRepository.findById(returnedRatingDTO.getId()).orElseThrow();
        assertThat(persistedRating.getRecipe().getId()).isEqualTo(recipe.getId());

        User loggedInUser = userRepository.findOneByLogin("user").orElseThrow();
        assertThat(persistedRating.getUser().getId()).isEqualTo(loggedInUser.getId());
    }

    @Test
    @Transactional
    void getAllRatings() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        // Get all the ratingList
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getRating() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        // Get the rating
        restRatingMockMvc
            .perform(get(ENTITY_API_URL_ID, rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rating.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingRating() throws Exception {
        // Get the rating
        restRatingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRating() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rating
        Rating updatedRating = ratingRepository.findById(rating.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRating are not directly saved in db
        em.detach(updatedRating);
        updatedRating
            .rate(UPDATED_RATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE);
        RatingDTO ratingDTO = ratingMapper.toDto(updatedRating);

        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRatingToMatchAllProperties(updatedRating);
    }

    @Test
    @Transactional
    void putNonExistingRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ratingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating.description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRatingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRating, rating), getPersistedRating(rating));
    }

    @Test
    @Transactional
    void fullUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating
            .rate(UPDATED_RATE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .createdDate(UPDATED_CREATED_DATE);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRatingUpdatableFieldsEquals(partialUpdatedRating, getPersistedRating(partialUpdatedRating));
    }

    @Test
    @Transactional
    void patchNonExistingRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRating() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rating.setId(longCount.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ratingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRating() throws Exception {
        // Initialize the database
        insertedRating = ratingRepository.saveAndFlush(rating);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rating
        restRatingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rating.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ratingRepository.count();
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

    protected Rating getPersistedRating(Rating rating) {
        return ratingRepository.findById(rating.getId()).orElseThrow();
    }

    protected void assertPersistedRatingToMatchAllProperties(Rating expectedRating) {
        assertRatingAllPropertiesEquals(expectedRating, getPersistedRating(expectedRating));
    }

    protected void assertPersistedRatingToMatchUpdatableProperties(Rating expectedRating) {
        assertRatingAllUpdatablePropertiesEquals(expectedRating, getPersistedRating(expectedRating));
    }
}
