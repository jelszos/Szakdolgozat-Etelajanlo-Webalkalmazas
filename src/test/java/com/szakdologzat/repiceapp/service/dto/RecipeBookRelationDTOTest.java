package com.szakdologzat.repiceapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeBookRelationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeBookRelationDTO.class);
        RecipeBookRelationDTO recipeBookRelationDTO1 = new RecipeBookRelationDTO();
        recipeBookRelationDTO1.setId(1L);
        RecipeBookRelationDTO recipeBookRelationDTO2 = new RecipeBookRelationDTO();
        assertThat(recipeBookRelationDTO1).isNotEqualTo(recipeBookRelationDTO2);
        recipeBookRelationDTO2.setId(recipeBookRelationDTO1.getId());
        assertThat(recipeBookRelationDTO1).isEqualTo(recipeBookRelationDTO2);
        recipeBookRelationDTO2.setId(2L);
        assertThat(recipeBookRelationDTO1).isNotEqualTo(recipeBookRelationDTO2);
        recipeBookRelationDTO1.setId(null);
        assertThat(recipeBookRelationDTO1).isNotEqualTo(recipeBookRelationDTO2);
    }
}
