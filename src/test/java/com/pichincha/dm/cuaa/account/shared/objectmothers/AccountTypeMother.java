package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;

public final class AccountTypeMother {

    // OpenAPI enum: SAVINGS, CURRENT
    private static final String[] VALID_ACCOUNT_TYPES = {"SAVINGS", "CURRENT"};

    private AccountTypeMother() {
    }

    public static AccountType random() {
        return create(FakerMother.faker().options().option(VALID_ACCOUNT_TYPES));
    }

    public static AccountType create(String value) {
        return new AccountType(value);
    }
}