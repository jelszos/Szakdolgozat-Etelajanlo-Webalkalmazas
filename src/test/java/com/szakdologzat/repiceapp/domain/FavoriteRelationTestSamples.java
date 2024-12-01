package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FavoriteRelationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static FavoriteRelation getFavoriteRelationSample1() {
        return new FavoriteRelation().id(1L);
    }

    public static FavoriteRelation getFavoriteRelationSample2() {
        return new FavoriteRelation().id(2L);
    }

    public static FavoriteRelation getFavoriteRelationRandomSampleGenerator() {
        return new FavoriteRelation().id(longCount.incrementAndGet());
    }
}
