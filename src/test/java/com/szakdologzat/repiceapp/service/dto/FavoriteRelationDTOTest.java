package com.szakdologzat.repiceapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.szakdologzat.repiceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavoriteRelationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteRelationDTO.class);
        FavoriteRelationDTO favoriteRelationDTO1 = new FavoriteRelationDTO();
        favoriteRelationDTO1.setId(1L);
        FavoriteRelationDTO favoriteRelationDTO2 = new FavoriteRelationDTO();
        assertThat(favoriteRelationDTO1).isNotEqualTo(favoriteRelationDTO2);
        favoriteRelationDTO2.setId(favoriteRelationDTO1.getId());
        assertThat(favoriteRelationDTO1).isEqualTo(favoriteRelationDTO2);
        favoriteRelationDTO2.setId(2L);
        assertThat(favoriteRelationDTO1).isNotEqualTo(favoriteRelationDTO2);
        favoriteRelationDTO1.setId(null);
        assertThat(favoriteRelationDTO1).isNotEqualTo(favoriteRelationDTO2);
    }
}
