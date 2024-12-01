package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.RecipeBookAsserts.*;
import static com.szakdologzat.repiceapp.domain.RecipeBookTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeBookMapperTest {

    private RecipeBookMapper recipeBookMapper;

    @BeforeEach
    void setUp() {
        recipeBookMapper = new RecipeBookMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecipeBookSample1();
        var actual = recipeBookMapper.toEntity(recipeBookMapper.toDto(expected));
        assertRecipeBookAllPropertiesEquals(expected, actual);
    }
}
