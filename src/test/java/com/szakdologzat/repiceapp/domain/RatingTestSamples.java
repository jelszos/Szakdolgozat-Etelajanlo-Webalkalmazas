package com.szakdologzat.repiceapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RatingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Rating getRatingSample1() {
        return new Rating().id(1L).rate(1).title("title1").description("description1").imageUrl("imageUrl1");
    }

    public static Rating getRatingSample2() {
        return new Rating().id(2L).rate(2).title("title2").description("description2").imageUrl("imageUrl2");
    }

    public static Rating getRatingRandomSampleGenerator() {
        return new Rating()
            .id(longCount.incrementAndGet())
            .rate(intCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString());
    }
}
