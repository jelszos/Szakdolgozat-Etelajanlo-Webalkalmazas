package com.szakdologzat.repiceapp.web.rest;

import static com.szakdologzat.repiceapp.domain.InstructionAsserts.*;
import static com.szakdologzat.repiceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szakdologzat.repiceapp.IntegrationTest;
import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.repository.InstructionRepository;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.mapper.InstructionMapper;
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
 * Integration tests for the {@link InstructionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InstructionResourceIT {

    private static final String DEFAULT_TITLE = "title";
    private static final String UPDATED_TITLE = "updated_title";

    private static final Integer DEFAULT_REQUIRED_TIME = 1;
    private static final Integer UPDATED_REQUIRED_TIME = 2;

    private static final String DEFAULT_DESCRIPTION = "description";
    private static final String UPDATED_DESCRIPTION = "updated_description";

    private static final String ENTITY_API_URL = "/api/instructions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InstructionRepository instructionRepository;

    @Autowired
    private InstructionMapper instructionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstructionMockMvc;

    private Instruction instruction;

    private Instruction insertedInstruction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instruction createEntity() {
        return new Instruction().title(DEFAULT_TITLE).requiredTime(DEFAULT_REQUIRED_TIME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instruction createUpdatedEntity() {
        return new Instruction().title(UPDATED_TITLE).requiredTime(UPDATED_REQUIRED_TIME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        instruction = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedInstruction != null) {
            instructionRepository.delete(insertedInstruction);
            insertedInstruction = null;
        }
    }

    @Test
    @Transactional
    void createInstruction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);
        var returnedInstructionDTO = om.readValue(
            restInstructionMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InstructionDTO.class
        );

        // Validate the Instruction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInstruction = instructionMapper.toEntity(returnedInstructionDTO);
        assertInstructionUpdatableFieldsEquals(returnedInstruction, getPersistedInstruction(returnedInstruction));

        insertedInstruction = returnedInstruction;
    }

    @Test
    @Transactional
    void createInstructionWithExistingId() throws Exception {
        // Create the Instruction with an existing ID
        instruction.setId(1L);
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstructionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInstructions() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        // Get all the instructionList
        restInstructionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instruction.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].requiredTime").value(hasItem(DEFAULT_REQUIRED_TIME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getInstruction() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        // Get the instruction
        restInstructionMockMvc
            .perform(get(ENTITY_API_URL_ID, instruction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instruction.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.requiredTime").value(DEFAULT_REQUIRED_TIME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingInstruction() throws Exception {
        // Get the instruction
        restInstructionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstruction() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instruction
        Instruction updatedInstruction = instructionRepository.findById(instruction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInstruction are not directly saved in db
        em.detach(updatedInstruction);
        updatedInstruction.title(UPDATED_TITLE).requiredTime(UPDATED_REQUIRED_TIME).description(UPDATED_DESCRIPTION);
        InstructionDTO instructionDTO = instructionMapper.toDto(updatedInstruction);

        restInstructionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instructionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInstructionToMatchAllProperties(updatedInstruction);
    }

    @Test
    @Transactional
    void putNonExistingInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instructionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(instructionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstructionWithPatch() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instruction using partial update
        Instruction partialUpdatedInstruction = new Instruction();
        partialUpdatedInstruction.setId(instruction.getId());

        partialUpdatedInstruction.requiredTime(UPDATED_REQUIRED_TIME);

        restInstructionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstruction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstruction))
            )
            .andExpect(status().isOk());

        // Validate the Instruction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstructionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInstruction, instruction),
            getPersistedInstruction(instruction)
        );
    }

    @Test
    @Transactional
    void fullUpdateInstructionWithPatch() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the instruction using partial update
        Instruction partialUpdatedInstruction = new Instruction();
        partialUpdatedInstruction.setId(instruction.getId());

        partialUpdatedInstruction.title(UPDATED_TITLE).requiredTime(UPDATED_REQUIRED_TIME).description(UPDATED_DESCRIPTION);

        restInstructionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstruction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstruction))
            )
            .andExpect(status().isOk());

        // Validate the Instruction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstructionUpdatableFieldsEquals(partialUpdatedInstruction, getPersistedInstruction(partialUpdatedInstruction));
    }

    @Test
    @Transactional
    void patchNonExistingInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, instructionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstruction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        instruction.setId(longCount.incrementAndGet());

        // Create the Instruction
        InstructionDTO instructionDTO = instructionMapper.toDto(instruction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstructionMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(instructionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Instruction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstruction() throws Exception {
        // Initialize the database
        insertedInstruction = instructionRepository.saveAndFlush(instruction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the instruction
        restInstructionMockMvc
            .perform(delete(ENTITY_API_URL_ID, instruction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return instructionRepository.count();
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

    protected Instruction getPersistedInstruction(Instruction instruction) {
        return instructionRepository.findById(instruction.getId()).orElseThrow();
    }

    protected void assertPersistedInstructionToMatchAllProperties(Instruction expectedInstruction) {
        assertInstructionAllPropertiesEquals(expectedInstruction, getPersistedInstruction(expectedInstruction));
    }

    protected void assertPersistedInstructionToMatchUpdatableProperties(Instruction expectedInstruction) {
        assertInstructionAllUpdatablePropertiesEquals(expectedInstruction, getPersistedInstruction(expectedInstruction));
    }
}
