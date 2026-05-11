package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;

public final class MovementMother {
    public static Movement random() {
        return new Movement(
                MovementIdMother.random(),
                AccountIdMother.random(),
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random()
        );
    }

    public static Movement withAccountId(AccountId accountId) {
        return new Movement(
                MovementIdMother.random(),
                accountId,
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random()
        );
    }

    public static Movement withId(MovementId id) {
        return new Movement(
                id,
                AccountIdMother.random(),
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random()
        );
    }
}