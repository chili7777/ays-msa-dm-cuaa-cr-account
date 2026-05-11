package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import reactor.core.publisher.Mono;

public interface PatchMovementInputPort {
    Mono<Void> patchMovement(MovementId movementId, Movement movement);
}