package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.repository.InstructionRepository;
import com.szakdologzat.repiceapp.service.InstructionService;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
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
 * REST controller for managing {@link com.szakdologzat.repiceapp.domain.Instruction}.
 */
@RestController
@RequestMapping("/api/instructions")
public class InstructionResource {

    private static final Logger LOG = LoggerFactory.getLogger(InstructionResource.class);

    private static final String ENTITY_NAME = "instruction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstructionService instructionService;

    private final InstructionRepository instructionRepository;

    public InstructionResource(InstructionService instructionService, InstructionRepository instructionRepository) {
        this.instructionService = instructionService;
        this.instructionRepository = instructionRepository;
    }

    /**
     * {@code POST  /instructions} : Create a new instruction.
     *
     * @param instructionDTO the instructionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instructionDTO, or with status {@code 400 (Bad Request)} if the instruction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InstructionDTO> createInstruction(@RequestBody InstructionDTO instructionDTO) throws URISyntaxException {
        LOG.debug("REST request to save Instruction : {}", instructionDTO);
        if (instructionDTO.getId() != null) {
            throw new BadRequestAlertException("A new instruction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        instructionDTO = instructionService.save(instructionDTO);
        return ResponseEntity.created(new URI("/api/instructions/" + instructionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, instructionDTO.getId().toString()))
            .body(instructionDTO);
    }

    /**
     * {@code PUT  /instructions/:id} : Updates an existing instruction.
     *
     * @param id the id of the instructionDTO to save.
     * @param instructionDTO the instructionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instructionDTO,
     * or with status {@code 400 (Bad Request)} if the instructionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instructionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstructionDTO> updateInstruction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstructionDTO instructionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Instruction : {}, {}", id, instructionDTO);
        if (instructionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instructionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instructionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        instructionDTO = instructionService.update(instructionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instructionDTO.getId().toString()))
            .body(instructionDTO);
    }

    /**
     * {@code PATCH  /instructions/:id} : Partial updates given fields of an existing instruction, field will ignore if it is null
     *
     * @param id the id of the instructionDTO to save.
     * @param instructionDTO the instructionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instructionDTO,
     * or with status {@code 400 (Bad Request)} if the instructionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the instructionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the instructionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InstructionDTO> partialUpdateInstruction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstructionDTO instructionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Instruction partially : {}, {}", id, instructionDTO);
        if (instructionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instructionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instructionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InstructionDTO> result = instructionService.partialUpdate(instructionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instructionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /instructions} : get all the instructions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instructions in body.
     */
    @GetMapping("")
    public List<InstructionDTO> getAllInstructions() {
        LOG.debug("REST request to get all Instructions");
        return instructionService.findAll();
    }

    /**
     * {@code GET  /instructions/:id} : get the "id" instruction.
     *
     * @param id the id of the instructionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instructionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstructionDTO> getInstruction(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Instruction : {}", id);
        Optional<InstructionDTO> instructionDTO = instructionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instructionDTO);
    }

    /**
     * {@code DELETE  /instructions/:id} : delete the "id" instruction.
     *
     * @param id the id of the instructionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstruction(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Instruction : {}", id);
        instructionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
