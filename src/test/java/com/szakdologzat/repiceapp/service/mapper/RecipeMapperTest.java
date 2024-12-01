package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.RecipeAsserts.*;
import static com.szakdologzat.repiceapp.domain.RecipeTestSamples.*;

import com.szakdologzat.repiceapp.service.FavoriteRelationService;
import com.szakdologzat.repiceapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RecipeMapperTest {

    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        recipeMapper = new RecipeMapperImpl();
        recipeMapper.recipeService = Mockito.mock(RecipeService.class);
        recipeMapper.favoriteRelationService = Mockito.mock(FavoriteRelationService.class);
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecipeSample1();
        var actual = recipeMapper.toEntity(recipeMapper.toDto(expected));
        assertRecipeAllPropertiesEquals(expected, actual);
    }
}
