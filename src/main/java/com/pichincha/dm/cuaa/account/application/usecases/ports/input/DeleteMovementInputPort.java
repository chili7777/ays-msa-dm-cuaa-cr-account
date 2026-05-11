package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import reactor.core.publisher.Mono;

public interface DeleteMovementInputPort {
    Mono<Void> deleteMovement(MovementId movementId);
}