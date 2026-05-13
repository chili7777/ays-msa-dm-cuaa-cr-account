package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsByCustomerAndAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementByCustomerAndAccountLister implements ListMovementsByCustomerAndAccountInputPort {

    private final ListMovementsOutputPort movementPersistence;

    @Override
    public Flux<Movement> listMovementsByCustomerAndAccount(CustomerId customerId, AccountId accountId, LocalDate fromDate, LocalDate toDate, String movementType) {
        return movementPersistence.findMovementsByCustomerAndAccountId(customerId, accountId);
    }
}