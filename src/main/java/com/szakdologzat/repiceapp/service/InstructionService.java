package com.szakdologzat.repiceapp.service;

import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.repository.InstructionRepository;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.mapper.InstructionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.szakdologzat.repiceapp.domain.Instruction}.
 */
@Service
@Transactional
public class InstructionService {

    private static final Logger LOG = LoggerFactory.getLogger(InstructionService.class);

    private final InstructionRepository instructionRepository;

    private final InstructionMapper instructionMapper;

    public InstructionService(InstructionRepository instructionRepository, InstructionMapper instructionMapper) {
        this.instructionRepository = instructionRepository;
        this.instructionMapper = instructionMapper;
    }

    /**
     * Save a instruction.
     *
     * @param instructionDTO the entity to save.
     * @return the persisted entity.
     */
    public InstructionDTO save(InstructionDTO instructionDTO) {
        LOG.debug("Request to save Instruction : {}", instructionDTO);
        Instruction instruction = instructionMapper.toEntity(instructionDTO);
        instruction = instructionRepository.save(instruction);
        return instructionMapper.toDto(instruction);
    }

    /**
     * Update a instruction.
     *
     * @param instructionDTO the entity to save.
     * @return the persisted entity.
     */
    public InstructionDTO update(InstructionDTO instructionDTO) {
        LOG.debug("Request to update Instruction : {}", instructionDTO);
        Instruction instruction = instructionMapper.toEntity(instructionDTO);
        instruction = instructionRepository.save(instruction);
        return instructionMapper.toDto(instruction);
    }

    /**
     * Partially update a instruction.
     *
     * @param instructionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InstructionDTO> partialUpdate(InstructionDTO instructionDTO) {
        LOG.debug("Request to partially update Instruction : {}", instructionDTO);

        return instructionRepository
            .findById(instructionDTO.getId())
            .map(existingInstruction -> {
                instructionMapper.partialUpdate(existingInstruction, instructionDTO);

                return existingInstruction;
            })
            .map(instructionRepository::save)
            .map(instructionMapper::toDto);
    }

    /**
     * Get all the instructions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InstructionDTO> findAll() {
        LOG.debug("Request to get all Instructions");
        return instructionRepository.findAll().stream().map(instructionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one instruction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstructionDTO> findOne(Long id) {
        LOG.debug("Request to get Instruction : {}", id);
        return instructionRepository.findById(id).map(instructionMapper::toDto);
    }

    /**
     * Delete the instruction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Instruction : {}", id);
        instructionRepository.deleteById(id);
    }
}
