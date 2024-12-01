package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.RecipeBookRelationTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeBookTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecipeBookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeBook.class);
        RecipeBook recipeBook1 = getRecipeBookSample1();
        RecipeBook recipeBook2 = new RecipeBook();
        assertThat(recipeBook1).isNotEqualTo(recipeBook2);

        recipeBook2.setId(recipeBook1.getId());
        assertThat(recipeBook1).isEqualTo(recipeBook2);

        recipeBook2 = getRecipeBookSample2();
        assertThat(recipeBook1).isNotEqualTo(recipeBook2);
    }

    @Test
    void recipeBookRelationTest() {
        RecipeBook recipeBook = getRecipeBookRandomSampleGenerator();
        RecipeBookRelation recipeBookRelationBack = getRecipeBookRelationRandomSampleGenerator();

        recipeBook.addRecipeBookRelation(recipeBookRelationBack);
        assertThat(recipeBook.getRecipeBookRelations()).containsOnly(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipeBook()).isEqualTo(recipeBook);

        recipeBook.removeRecipeBookRelation(recipeBookRelationBack);
        assertThat(recipeBook.getRecipeBookRelations()).doesNotContain(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipeBook()).isNull();

        recipeBook.recipeBookRelations(new HashSet<>(Set.of(recipeBookRelationBack)));
        assertThat(recipeBook.getRecipeBookRelations()).containsOnly(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipeBook()).isEqualTo(recipeBook);

        recipeBook.setRecipeBookRelations(new HashSet<>());
        assertThat(recipeBook.getRecipeBookRelations()).doesNotContain(recipeBookRelationBack);
        assertThat(recipeBookRelationBack.getRecipeBook()).isNull();
    }
}
