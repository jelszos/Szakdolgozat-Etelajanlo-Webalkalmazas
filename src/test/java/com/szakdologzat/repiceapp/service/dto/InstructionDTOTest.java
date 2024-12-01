package com.szakdologzat.repiceapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstructionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstructionDTO.class);
        InstructionDTO instructionDTO1 = new InstructionDTO();
        instructionDTO1.setId(1L);
        InstructionDTO instructionDTO2 = new InstructionDTO();
        assertThat(instructionDTO1).isNotEqualTo(instructionDTO2);
        instructionDTO2.setId(instructionDTO1.getId());
        assertThat(instructionDTO1).isEqualTo(instructionDTO2);
        instructionDTO2.setId(2L);
        assertThat(instructionDTO1).isNotEqualTo(instructionDTO2);
        instructionDTO1.setId(null);
        assertThat(instructionDTO1).isNotEqualTo(instructionDTO2);
    }
}
