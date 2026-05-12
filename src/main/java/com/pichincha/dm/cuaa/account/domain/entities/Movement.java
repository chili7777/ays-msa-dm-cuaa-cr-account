package com.pichincha.dm.cuaa.account.domain.entities;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Amount;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementDate;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;

public record Movement(
    MovementId movementId,
    AccountId accountId,
    MovementDate movementDate,
    MovementType movementType,
    Amount amount,
    Balance balance,
    Status status
) {}