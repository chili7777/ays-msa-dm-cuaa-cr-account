package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.github.javafaker.Faker;

public final class FakerMother {

    private static final Faker FAKER = Faker.instance();

    private FakerMother() {
    }

    public static Faker faker() {
        return FAKER;
    }

    public static Faker random() {
        return FAKER;
    }
}