package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Age;

public final class AgeMother {
    public static Age random() {
        return new Age(FakerMother.random().number().numberBetween(18, 99));
    }
}