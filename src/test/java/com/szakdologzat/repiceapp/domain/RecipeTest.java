package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.FavoriteRelationTestSamples.*;
import static com.szakdologzat.repiceapp.domain.InstructionTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RatingTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeBookRelationTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipe.class);
        Recipe recipe1 = getRecipeSample1();
        Recipe recipe2 = new Recipe();
        assertThat(recipe1).isNotEqualTo(recipe2);

        recipe2.setId(recipe1.getId());
        assertThat(recipe1).isEqualTo(recipe2);

        recipe2 = getRecipeSample2();
        assertThat(recipe1).isNotEqualTo(recipe2);
    }

    @Test
    void instructionTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        Instruction instructionBack = getInstructionRandomSampleGenerator();

        recipe.addInstruction(instructionBack);
        assertThat(recipe.getInstructions()).containsOnly(instructionBack);
        assertThat(instructionBack.getRecipe()).isEqualTo(recipe);

        recipe.removeInstruction(instructionBack);
        assertThat(recipe.getInstructions()).doesNotContain(instructionBack);
        assertThat(instructionBack.getRecipe()).isNull();

        recipe.instructions(new HashSet<>(Set.of(instructionBack)));
        assertThat(recipe.getInstructions()).containsOnly(instructionBack);
        assertThat(instructionBack.getRecipe()).isEqualTo(recipe);

        recipe.setInstructions(new HashSet<>());
        assertThat(recipe.getInstructions()).doesNotContain(instructionBack);
        assertThat(instructionBack.getRecipe()).isNull();
    }

    @Test
    void ratingTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        Rating ratingBack = getRatingRandomSampleGenerator();

        recipe.addRating(ratingBack);
        assertThat(recipe.getRatings()).containsOnly(ratingBack);
        assertThat(ratingBack.getRecipe()).isEqualTo(recipe);

        recipe.removeRating(ratingBack);
        assertThat(recipe.getRatings()).doesNotContain(ratingBack);
        assertThat(ratingBack.getRecipe()).isNull();

        recipe.ratings(new HashSet<>(Set.of(ratingBack)));
        assertThat(recipe.getRatings()).containsOnly(ratingBack);
        assertThat(ratingBack.getRecipe()).isEqualTo(recipe);

        recipe.setRatings(new HashSet<>());
        assertThat(recipe.getRatings()).doesNotContain(ratingBack);
        assertThat(ratingBack.getRecipe()).isNull();
    }

    @Test
    void recipeBookRelationTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        RecipeBookRelation recipeBookRelationBack = getRecipeBookRelationRandomSampleGenerator();

        recipe.addRecipeBookRelation(recipeBookRelationBack);
        assertThat(recipe.getRecipeBookRelations()).containsOnly(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipe()).isEqualTo(recipe);

        recipe.removeRecipeBookRelation(recipeBookRelationBack);
        assertThat(recipe.getRecipeBookRelations()).doesNotContain(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipe()).isNull();

        recipe.recipeBookRelations(new HashSet<>(Set.of(recipeBookRelationBack)));
        assertThat(recipe.getRecipeBookRelations()).containsOnly(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipe()).isEqualTo(recipe);

        recipe.setRecipeBookRelations(new HashSet<>());
        assertThat(recipe.getRecipeBookRelations()).doesNotContain(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipe()).isNull();
    }

    @Test
    void favoriteRelationTest() {
        Recipe recipe = getRecipeRandomSampleGenerator();
        FavoriteRelation favoriteRelationBack = getFavoriteRelationRandomSampleGenerator();

        recipe.addFavoriteRelation(favoriteRelationBack);
        assertThat(recipe.getFavoriteRelations()).containsOnly(favoriteRelationBack);
        assertThat(favoriteRelationBack.getRecipe()).isEqualTo(recipe);

        recipe.removeFavoriteRelation(favoriteRelationBack);
        assertThat(recipe.getFavoriteRelations()).doesNotContain(favoriteRelationBack);
        assertThat(favoriteRelationBack.getRecipe()).isNull();

        recipe.favoriteRelations(new HashSet<>(Set.of(favoriteRelationBack)));
        assertThat(recipe.getFavoriteRelations()).containsOnly(favoriteRelationBack);
        assertThat(favoriteRelationBack.getRecipe()).isEqualTo(recipe);

        recipe.setFavoriteRelations(new HashSet<>());
        assertThat(recipe.getFavoriteRelations()).doesNotContain(favoriteRelationBack);
        assertThat(favoriteRelationBack.getRecipe()).isNull();
    }
}
