package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceMovementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementReplacer implements ReplaceMovementInputPort {

    private final ReplaceMovementOutputPort repository;

    @Override
    public Mono<Void> replaceMovement(MovementId movementId, Movement movement) {
        return repository.update(movementId, movement);
    }
}