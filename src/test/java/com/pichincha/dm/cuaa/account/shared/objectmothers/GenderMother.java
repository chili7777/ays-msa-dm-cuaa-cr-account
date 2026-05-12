package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Gender;

public final class GenderMother {
    public static Gender random() {
        return new Gender(FakerMother.random().options().option("MALE", "FEMALE", "OTHER"));
    }
}