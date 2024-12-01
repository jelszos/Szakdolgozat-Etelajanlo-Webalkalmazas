package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.RecipeBookRelationAsserts.*;
import static com.szakdologzat.repiceapp.domain.RecipeBookRelationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeBookRelationMapperTest {

    private RecipeBookRelationMapper recipeBookRelationMapper;

    @BeforeEach
    void setUp() {
        recipeBookRelationMapper = new RecipeBookRelationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecipeBookRelationSample1();
        var actual = recipeBookRelationMapper.toEntity(recipeBookRelationMapper.toDto(expected));
        assertRecipeBookRelationAllPropertiesEquals(expected, actual);
    }
}
