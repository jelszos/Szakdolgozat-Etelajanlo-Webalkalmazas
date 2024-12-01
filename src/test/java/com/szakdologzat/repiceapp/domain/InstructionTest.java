package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.IngredientTestSamples.*;
import static com.szakdologzat.repiceapp.domain.InstructionTestSamples.*;
import static com.szakdologzat.repiceapp.domain.RecipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InstructionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instruction.class);
        Instruction instruction1 = getInstructionSample1();
        Instruction instruction2 = new Instruction();
        assertThat(instruction1).isNotEqualTo(instruction2);

        instruction2.setId(instruction1.getId());
        assertThat(instruction1).isEqualTo(instruction2);

        instruction2 = getInstructionSample2();
        assertThat(instruction1).isNotEqualTo(instruction2);
    }

    @Test
    void ingredientTest() {
        Instruction instruction = getInstructionRandomSampleGenerator();
        Ingredient ingredientBack = getIngredientRandomSampleGenerator();

        instruction.addIngredient(ingredientBack);
        assertThat(instruction.getIngredients()).containsOnly(ingredientBack);
        assertThat(ingredientBack.getInstruction()).isEqualTo(instruction);

        instruction.removeIngredient(ingredientBack);
        assertThat(instruction.getIngredients()).doesNotContain(ingredientBack);
        assertThat(ingredientBack.getInstruction()).isNull();

        instruction.ingredients(new HashSet<>(Set.of(ingredientBack)));
        assertThat(instruction.getIngredients()).containsOnly(ingredientBack);
        assertThat(ingredientBack.getInstruction()).isEqualTo(instruction);

        instruction.setIngredients(new HashSet<>());
        assertThat(instruction.getIngredients()).doesNotContain(ingredientBack);
        assertThat(ingredientBack.getInstruction()).isNull();
    }

    @Test
    void recipeTest() {
        Instruction instruction = getInstructionRandomSampleGenerator();
        Recipe recipeBack = getRecipeRandomSampleGenerator();

        instruction.setRecipe(recipeBack);
        assertThat(instruction.getRecipe()).isEqualTo(recipeBack);

        instruction.recipe(null);
        assertThat(instruction.getRecipe()).isNull();
    }
}
