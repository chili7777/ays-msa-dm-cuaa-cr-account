package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;

public final class IdentificationMother {
    public static Identification random() {
        return new Identification(FakerMother.random().idNumber().valid());
    }
}