package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListMovementsInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListMovementsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class MovementLister implements ListMovementsInputPort {

    private final ListMovementsOutputPort repository;

    @Override
    public Flux<Movement> listMovements(LocalDate fromDate, LocalDate toDate, String movementType) {
        if (fromDate == null && toDate == null && movementType == null) {
            return repository.findAllMovements();
        }

        LocalDateTime start = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime end = toDate != null ? toDate.atTime(LocalTime.MAX) : null;

        return repository.findMovementsByFilters(null, start, end, movementType);
    }
}