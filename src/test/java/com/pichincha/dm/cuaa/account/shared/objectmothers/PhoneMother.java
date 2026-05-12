package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Phone;

public final class PhoneMother {
    public static Phone random() {
        return new Phone(FakerMother.random().number().digits(10));
    }
}