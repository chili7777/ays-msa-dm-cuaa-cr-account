package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;

public final class BalanceMother {
    public static Balance random() {
        return new Balance(FakerMother.random().number().randomDouble(2, 0, 10000));
    }

    public static Balance create(Double value) {
        return new Balance(value);
    }
}