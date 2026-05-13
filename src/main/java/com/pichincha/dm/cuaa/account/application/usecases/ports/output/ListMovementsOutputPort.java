package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.time.LocalDateTime;
import reactor.core.publisher.Flux;

public interface ListMovementsOutputPort {
    Flux<Movement> findAllMovements();
    Flux<Movement> findMovementsByAccountId(AccountId accountId);
    Flux<Movement> findMovementsByCustomerAndAccountId(CustomerId customerId, AccountId accountId);
    Flux<Movement> findMovementsByFilters(AccountId accountId, LocalDateTime start, LocalDateTime end, String type);
}