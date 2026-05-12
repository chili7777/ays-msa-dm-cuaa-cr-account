package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import reactor.core.publisher.Flux;

public interface ListMovementsOutputPort {
    Flux<Movement> findAllMovements();
    Flux<Movement> findMovementsByAccountId(AccountId accountId);
}