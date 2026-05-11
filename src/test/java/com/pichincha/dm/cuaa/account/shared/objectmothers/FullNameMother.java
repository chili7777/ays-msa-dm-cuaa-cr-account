package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.FullName;

public final class FullNameMother {
    public static FullName random() {
        return new FullName(FakerMother.random().name().fullName());
    }
}