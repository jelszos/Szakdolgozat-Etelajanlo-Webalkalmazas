package com.szakdologzat.repiceapp.service.mapper;

import static com.szakdologzat.repiceapp.domain.FavoriteRelationAsserts.*;
import static com.szakdologzat.repiceapp.domain.FavoriteRelationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavoriteRelationMapperTest {

    private FavoriteRelationMapper favoriteRelationMapper;

    @BeforeEach
    void setUp() {
        favoriteRelationMapper = new FavoriteRelationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFavoriteRelationSample1();
        var actual = favoriteRelationMapper.toEntity(favoriteRelationMapper.toDto(expected));
        assertFavoriteRelationAllPropertiesEquals(expected, actual);
    }
}
