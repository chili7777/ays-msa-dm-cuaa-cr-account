package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsByAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementByAccountLister implements ListMovementsByAccountInputPort {

    private final ListMovementsOutputPort movementPersistence;

    @Override
    public Flux<Movement> listMovementsByAccount(AccountId accountId, LocalDate fromDate, LocalDate toDate, String movementType) {
        if (fromDate == null && toDate == null && movementType == null) {
            return movementPersistence.findMovementsByAccountId(accountId);
        }

        LocalDateTime start = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime end = toDate != null ? toDate.atTime(LocalTime.MAX) : null;

        return movementPersistence.findMovementsByFilters(accountId, start, end, movementType);
    }
}