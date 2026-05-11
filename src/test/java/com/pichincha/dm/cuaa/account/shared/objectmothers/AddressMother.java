package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Address;

public final class AddressMother {
    public static Address random() {
        return new Address(FakerMother.random().address().fullAddress());
    }
}