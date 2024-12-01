package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.Instruction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Instruction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {}
