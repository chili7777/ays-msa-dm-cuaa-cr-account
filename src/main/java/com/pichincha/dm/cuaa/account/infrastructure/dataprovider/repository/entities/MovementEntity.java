package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities;

import java.time.LocalDateTime;

public record MovementEntity(
    String movementId,
    String accountId,
    LocalDateTime movementDate,
    String movementType,
    Double amount
) {}