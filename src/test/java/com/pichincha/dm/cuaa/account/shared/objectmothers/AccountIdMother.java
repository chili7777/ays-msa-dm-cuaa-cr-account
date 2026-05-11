package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

public final class AccountIdMother {

    private AccountIdMother() {
    }

    public static AccountId random() {
        return create(UuidMother.random().toString());
    }

    public static AccountId create(String value) {
        return new AccountId(value);
    }
}