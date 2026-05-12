package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsByAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementByAccountLister implements ListMovementsByAccountInputPort {

    private final ListMovementsOutputPort movementPersistence;

    @Override
    public Flux<Movement> listMovementsByAccount(AccountId accountId) {
        return movementPersistence.findMovementsByAccountId(accountId);
    }
}