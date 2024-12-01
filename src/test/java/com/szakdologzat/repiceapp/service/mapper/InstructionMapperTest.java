package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.InstructionAsserts.*;
import static com.szakdologzat.repiceapp.domain.InstructionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstructionMapperTest {

    private InstructionMapper instructionMapper;

    @BeforeEach
    void setUp() {
        instructionMapper = new InstructionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInstructionSample1();
        var actual = instructionMapper.toEntity(instructionMapper.toDto(expected));
        assertInstructionAllPropertiesEquals(expected, actual);
    }
}
