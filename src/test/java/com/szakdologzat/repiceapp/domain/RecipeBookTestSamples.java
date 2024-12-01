package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeBookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static RecipeBook getRecipeBookSample1() {
        return new RecipeBook().id(1L).title("title1").theme("theme1").description("description1").tags("tags1");
    }

    public static RecipeBook getRecipeBookSample2() {
        return new RecipeBook().id(2L).title("title2").theme("theme2").description("description2").tags("tags2");
    }

    public static RecipeBook getRecipeBookRandomSampleGenerator() {
        return new RecipeBook()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .theme(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .tags(UUID.randomUUID().toString());
    }
}
