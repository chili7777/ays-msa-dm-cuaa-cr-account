package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;

public final class InitialBalanceMother {

    private static final long MIN_BALANCE = 0;
    private static final long MAX_BALANCE = 100_000;
    private static final int DECIMAL_PLACES = 2;

    private InitialBalanceMother() {
    }

    public static InitialBalance random() {
        return create(FakerMother.faker().number().randomDouble(DECIMAL_PLACES, MIN_BALANCE, MAX_BALANCE));
    }

    public static InitialBalance create(Double value) {
        return new InitialBalance(value);
    }
}