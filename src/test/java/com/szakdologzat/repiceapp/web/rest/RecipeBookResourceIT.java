package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.RecipeBookAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
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
 * Integration tests for the {@link RecipeBookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecipeBookResourceIT {

    private static final String DEFAULT_TITLE = "title";
    private static final String UPDATED_TITLE = "updated_title";

    private static final String DEFAULT_THEME = "theme";
    private static final String UPDATED_THEME = "updated_theme";

    private static final String DEFAULT_DESCRIPTION = "description";
    private static final String UPDATED_DESCRIPTION = "updated_description";

    private static final String DEFAULT_TAGS = "tags";
    private static final String UPDATED_TAGS = "updated_tags";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/recipe-books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecipeBookRepository recipeBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeBookMapper recipeBookMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeBookMockMvc;

    private RecipeBook recipeBook;

    private RecipeBook insertedRecipeBook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeBook createEntity() {
        return new RecipeBook()
            .title(DEFAULT_TITLE)
            .theme(DEFAULT_THEME)
            .description(DEFAULT_DESCRIPTION)
            .tags(DEFAULT_TAGS)
            .createdDate(DEFAULT_CREATED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeBook createUpdatedEntity() {
        return new RecipeBook()
            .title(UPDATED_TITLE)
            .theme(UPDATED_THEME)
            .description(UPDATED_DESCRIPTION)
            .tags(UPDATED_TAGS)
            .createdDate(UPDATED_CREATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        recipeBook = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecipeBook != null) {
            recipeBookRepository.delete(insertedRecipeBook);
            insertedRecipeBook = null;
        }
    }

    @Test
    @Transactional
    void createRecipeBook() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);
        var returnedRecipeBookDTO = om.readValue(
            restRecipeBookMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeBookDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecipeBookDTO.class
        );

        // Validate the RecipeBook in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecipeBook = recipeBookMapper.toEntity(returnedRecipeBookDTO);
        assertRecipeBookUpdatableFieldsEquals(returnedRecipeBook, getPersistedRecipeBook(returnedRecipeBook));

        insertedRecipeBook = returnedRecipeBook;
    }

    @Test
    @Transactional
    void createRecipeBookWithExistingId() throws Exception {
        // Create the RecipeBook with an existing ID
        recipeBook.setId(1L);
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeBookMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    /*@Test
    @Transactional
    void getAllRecipeBooks() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        // Get all the recipeBookList
        restRecipeBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }*/

    @Test
    @Transactional
    void getRecipeBook() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        // Get the recipeBook
        restRecipeBookMockMvc
            .perform(get(ENTITY_API_URL_ID, recipeBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeBook.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS));
    }

    @Test
    @Transactional
    void getNonExistingRecipeBook() throws Exception {
        // Get the recipeBook
        restRecipeBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecipeBook() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBook
        RecipeBook updatedRecipeBook = recipeBookRepository.findById(recipeBook.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecipeBook are not directly saved in db
        em.detach(updatedRecipeBook);
        updatedRecipeBook
            .title(UPDATED_TITLE)
            .theme(UPDATED_THEME)
            .description(UPDATED_DESCRIPTION)
            .tags(UPDATED_TAGS)
            .createdDate(UPDATED_CREATED_DATE);
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(updatedRecipeBook);

        restRecipeBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeBookDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecipeBookToMatchAllProperties(updatedRecipeBook);
    }

    @Test
    @Transactional
    void putNonExistingRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeBookDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recipeBookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeBookWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBook using partial update
        RecipeBook partialUpdatedRecipeBook = new RecipeBook();
        partialUpdatedRecipeBook.setId(recipeBook.getId());

        partialUpdatedRecipeBook.description(UPDATED_DESCRIPTION).createdDate(UPDATED_CREATED_DATE);

        restRecipeBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeBook.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeBook))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBook in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeBookUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRecipeBook, recipeBook),
            getPersistedRecipeBook(recipeBook)
        );
    }

    @Test
    @Transactional
    void fullUpdateRecipeBookWithPatch() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recipeBook using partial update
        RecipeBook partialUpdatedRecipeBook = new RecipeBook();
        partialUpdatedRecipeBook.setId(recipeBook.getId());

        partialUpdatedRecipeBook
            .title(UPDATED_TITLE)
            .theme(UPDATED_THEME)
            .description(UPDATED_DESCRIPTION)
            .tags(UPDATED_TAGS)
            .createdDate(UPDATED_CREATED_DATE);

        restRecipeBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeBook.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecipeBook))
            )
            .andExpect(status().isOk());

        // Validate the RecipeBook in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecipeBookUpdatableFieldsEquals(partialUpdatedRecipeBook, getPersistedRecipeBook(partialUpdatedRecipeBook));
    }

    @Test
    @Transactional
    void patchNonExistingRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipeBookDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipeBook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recipeBook.setId(longCount.incrementAndGet());

        // Create the RecipeBook
        RecipeBookDTO recipeBookDTO = recipeBookMapper.toDto(recipeBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeBookMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recipeBookDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeBook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipeBook() throws Exception {
        // Initialize the database
        insertedRecipeBook = recipeBookRepository.saveAndFlush(recipeBook);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recipeBook
        restRecipeBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipeBook.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recipeBookRepository.count();
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

    protected RecipeBook getPersistedRecipeBook(RecipeBook recipeBook) {
        return recipeBookRepository.findById(recipeBook.getId()).orElseThrow();
    }

    protected void assertPersistedRecipeBookToMatchAllProperties(RecipeBook expectedRecipeBook) {
        assertRecipeBookAllPropertiesEquals(expectedRecipeBook, getPersistedRecipeBook(expectedRecipeBook));
    }

    protected void assertPersistedRecipeBookToMatchUpdatableProperties(RecipeBook expectedRecipeBook) {
        assertRecipeBookAllUpdatablePropertiesEquals(expectedRecipeBook, getPersistedRecipeBook(expectedRecipeBook));
    }
}
