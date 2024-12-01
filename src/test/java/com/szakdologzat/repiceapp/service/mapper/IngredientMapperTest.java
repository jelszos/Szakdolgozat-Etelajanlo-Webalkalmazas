package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.IngredientAsserts.*;
import static com.szakdologzat.repiceapp.domain.IngredientTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientMapperTest {

    private IngredientMapper ingredientMapper;

    @BeforeEach
    void setUp() {
        ingredientMapper = new IngredientMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIngredientSample1();
        var actual = ingredientMapper.toEntity(ingredientMapper.toDto(expected));
        assertIngredientAllPropertiesEquals(expected, actual);
    }
}
