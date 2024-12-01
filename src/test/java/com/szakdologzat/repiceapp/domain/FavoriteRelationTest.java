package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.FavoriteRelationTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavoriteRelationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteRelation.class);
        FavoriteRelation favoriteRelation1 = getFavoriteRelationSample1();
        FavoriteRelation favoriteRelation2 = new FavoriteRelation();
        assertThat(favoriteRelation1).isNotEqualTo(favoriteRelation2);

        favoriteRelation2.setId(favoriteRelation1.getId());
        assertThat(favoriteRelation1).isEqualTo(favoriteRelation2);

        favoriteRelation2 = getFavoriteRelationSample2();
        assertThat(favoriteRelation1).isNotEqualTo(favoriteRelation2);
    }

    @Test
    void recipeTest() {
        FavoriteRelation favoriteRelation = getFavoriteRelationRandomSampleGenerator();
        Recipe recipeBack = getRecipeRandomSampleGenerator();

        favoriteRelation.setRecipe(recipeBack);
        assertThat(favoriteRelation.getRecipe()).isEqualTo(recipeBack);

        favoriteRelation.recipe(null);
        assertThat(favoriteRelation.getRecipe()).isNull();
    }
}
