package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Description;

public final class DescriptionMother {
    public static Description random() {
        return new Description(FakerMother.random().lorem().sentence());
    }
}