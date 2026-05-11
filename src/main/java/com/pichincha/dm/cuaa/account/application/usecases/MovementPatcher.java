package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchMovementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementPatcher implements PatchMovementInputPort {

    private final PatchMovementOutputPort repository;

    @Override
    public Mono<Void> patchMovement(MovementId movementId, Movement movement) {
        return repository.patch(movementId, movement);
    }
}