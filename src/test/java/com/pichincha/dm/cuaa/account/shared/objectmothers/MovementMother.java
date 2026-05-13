package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Description;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public final class MovementMother {
    public static Movement random() {
        return new Movement(
                MovementIdMother.random(),
                AccountIdMother.random(),
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random(),
                BalanceMother.random(),
                StatusMother.random(),
                DescriptionMother.random()
        );
    }

    public static Movement withAccountId(AccountId accountId) {
        return new Movement(
                MovementIdMother.random(),
                accountId,
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random(),
                BalanceMother.random(),
                StatusMother.random(),
                DescriptionMother.random()
        );
    }

    public static Movement withId(MovementId id, AccountId accountId) {
        return new Movement(
                id,
                accountId,
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random(),
                BalanceMother.random(),
                StatusMother.random(),
                DescriptionMother.random()
        );
    }

    public static Movement withId(MovementId id) {
        return new Movement(
                id,
                AccountIdMother.random(),
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random(),
                BalanceMother.random(),
                StatusMother.random(),
                DescriptionMother.random()
        );
    }

    public static Movement create(MovementId movementId,
                                  AccountId accountId,
                                  Double amount,
                                  String movementType,
                                  Double balance,
                                  Boolean status) {
        return new Movement(
                movementId,
                accountId,
                MovementDateMother.random(),
                new com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementType(movementType),
                new com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Amount(amount),
                balance != null ? new Balance(balance) : null,
                status != null ? new Status(status) : null,
                DescriptionMother.random()
        );
    }
}