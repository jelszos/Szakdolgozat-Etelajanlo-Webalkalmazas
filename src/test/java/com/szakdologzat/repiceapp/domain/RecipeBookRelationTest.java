package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.RecipeBookRelationTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeBookTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeBookRelationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeBookRelation.class);
        RecipeBookRelation recipeBookRelation1 = getRecipeBookRelationSample1();
        RecipeBookRelation recipeBookRelation2 = new RecipeBookRelation();
        assertThat(recipeBookRelation1).isNotEqualTo(recipeBookRelation2);

        recipeBookRelation2.setId(recipeBookRelation1.getId());
        assertThat(recipeBookRelation1).isEqualTo(recipeBookRelation2);

        recipeBookRelation2 = getRecipeBookRelationSample2();
        assertThat(recipeBookRelation1).isNotEqualTo(recipeBookRelation2);
    }

    @Test
    void recipeTest() {
        RecipeBookRelation recipeBookRelation = getRecipeBookRelationRandomSampleGenerator();
        Recipe recipeBack = getRecipeRandomSampleGenerator();

        recipeBookRelation.setRecipe(recipeBack);
        assertThat(recipeBookRelation.getRecipe()).isEqualTo(recipeBack);

        recipeBookRelation.recipe(null);
        assertThat(recipeBookRelation.getRecipe()).isNull();
    }

    @Test
    void recipeBookTest() {
        RecipeBookRelation recipeBookRelation = getRecipeBookRelationRandomSampleGenerator();
        RecipeBook recipeBookBack = getRecipeBookRandomSampleGenerator();

        recipeBookRelation.setRecipeBook(recipeBookBack);
        assertThat(recipeBookRelation.getRecipeBook()).isEqualTo(recipeBookBack);

        recipeBookRelation.recipeBook(null);
        assertThat(recipeBookRelation.getRecipeBook()).isNull();
    }
}
