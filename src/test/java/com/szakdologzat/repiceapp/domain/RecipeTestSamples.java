package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Recipe getRecipeSample1() {
        return new Recipe().id(1L).title("title1").description("description1").ingredientNames("ingredientNames1").imageUrl("imageUrl1");
    }

    public static Recipe getRecipeSample2() {
        return new Recipe().id(2L).title("title2").description("description2").ingredientNames("ingredientNames2").imageUrl("imageUrl2");
    }

    public static Recipe getRecipeRandomSampleGenerator() {
        return new Recipe()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .ingredientNames(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString());
    }
}
