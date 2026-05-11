package com.pichincha.dm.cuaa.account.shared.objectmothers;

import java.util.UUID;

public final class UuidMother {

    private UuidMother() {
    }

    public static UUID random() {
        return UUID.fromString(randomAsString());
    }

    public static String randomAsString() {
        return FakerMother.faker().internet().uuid();
    }
}