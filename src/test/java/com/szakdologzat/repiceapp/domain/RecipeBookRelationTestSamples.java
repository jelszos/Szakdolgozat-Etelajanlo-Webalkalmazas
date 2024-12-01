package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeBookRelationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static RecipeBookRelation getRecipeBookRelationSample1() {
        return new RecipeBookRelation().id(1L);
    }

    public static RecipeBookRelation getRecipeBookRelationSample2() {
        return new RecipeBookRelation().id(2L);
    }

    public static RecipeBookRelation getRecipeBookRelationRandomSampleGenerator() {
        return new RecipeBookRelation().id(longCount.incrementAndGet());
    }
}
