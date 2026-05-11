package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Amount;

public final class AmountMother {
    public static Amount random() {
        return new Amount(FakerMother.random().number().randomDouble(2, 1, 10000));
    }
}