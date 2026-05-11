package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;

public final class CustomerIdMother {
    public static CustomerId random() {
        return new CustomerId(UuidMother.random());
    }
}