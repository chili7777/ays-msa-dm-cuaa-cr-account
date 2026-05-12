package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import reactor.core.publisher.Flux;

public interface ListMovementsByAccountInputPort {
    Flux<Movement> listMovementsByAccount(AccountId accountId);
}