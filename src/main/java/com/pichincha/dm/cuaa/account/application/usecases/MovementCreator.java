package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateMovementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementCreator implements CreateMovementInputPort {

    private final CreateMovementOutputPort repository;

    @Override
    public Mono<Void> createMovement(Movement movement) {
        if (movement.movementId() == null) {
            movement = new Movement(
                    new MovementId(UUID.randomUUID().toString()),
                    movement.accountId(),
                    movement.movementDate(),
                    movement.movementType(),
                    movement.amount()
            );
        }
        return repository.save(movement);
    }
}