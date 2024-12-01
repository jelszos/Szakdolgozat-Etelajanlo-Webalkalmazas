package com.szakdologzat.repiceapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeBookDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeBookDTO.class);
        RecipeBookDTO recipeBookDTO1 = new RecipeBookDTO();
        recipeBookDTO1.setId(1L);
        RecipeBookDTO recipeBookDTO2 = new RecipeBookDTO();
        assertThat(recipeBookDTO1).isNotEqualTo(recipeBookDTO2);
        recipeBookDTO2.setId(recipeBookDTO1.getId());
        assertThat(recipeBookDTO1).isEqualTo(recipeBookDTO2);
        recipeBookDTO2.setId(2L);
        assertThat(recipeBookDTO1).isNotEqualTo(recipeBookDTO2);
        recipeBookDTO1.setId(null);
        assertThat(recipeBookDTO1).isNotEqualTo(recipeBookDTO2);
    }
}
