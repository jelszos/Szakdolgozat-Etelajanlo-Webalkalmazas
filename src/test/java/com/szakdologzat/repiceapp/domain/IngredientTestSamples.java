package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IngredientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Ingredient getIngredientSample1() {
        return new Ingredient().id(1L).name("name1").amount(1);
    }

    public static Ingredient getIngredientSample2() {
        return new Ingredient().id(2L).name("name2").amount(2);
    }

    public static Ingredient getIngredientRandomSampleGenerator() {
        return new Ingredient().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).amount(intCount.incrementAndGet());
    }
}
