package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetMovementByIdInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetMovementByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementFinder implements GetMovementByIdInputPort {

    private final GetMovementByIdOutputPort repository;

    @Override
    public Mono<Movement> getMovementById(MovementId movementId) {
        return repository.findById(movementId);
    }
}