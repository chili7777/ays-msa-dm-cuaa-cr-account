package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.DeleteMovementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementDeactivator implements DeleteMovementInputPort {

    private final DeleteMovementOutputPort repository;

    @Override
    public Mono<Void> deleteMovement(MovementId movementId) {
        return repository.deactivate(movementId);
    }
}