package com.szakdologzat.repiceapp.domain;

import static com.szakdologzat.repiceapp.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRatingAllPropertiesEquals(Rating expected, Rating actual) {
        assertRatingAutoGeneratedPropertiesEquals(expected, actual);
        assertRatingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRatingAllUpdatablePropertiesEquals(Rating expected, Rating actual) {
        assertRatingUpdatableFieldsEquals(expected, actual);
        assertRatingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRatingAutoGeneratedPropertiesEquals(Rating expected, Rating actual) {
        assertThat(expected)
            .as("Verify Rating auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRatingUpdatableFieldsEquals(Rating expected, Rating actual) {
        assertThat(expected)
            .as("Verify Rating relevant properties")
            .satisfies(e -> assertThat(e.getRate()).as("check rate").isEqualTo(actual.getRate()))
            .satisfies(e -> assertThat(e.getTitle()).as("check title").isEqualTo(actual.getTitle()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getImageUrl()).as("check imageUrl").isEqualTo(actual.getImageUrl()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRatingUpdatableRelationshipsEquals(Rating expected, Rating actual) {
        assertThat(expected)
            .as("Verify Rating relationships")
            .satisfies(e -> assertThat(e.getRecipe()).as("check recipe").isEqualTo(actual.getRecipe()));
    }
}
