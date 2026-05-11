package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import reactor.core.publisher.Mono;

public interface ReplaceMovementOutputPort {
    Mono<Void> update(MovementId movementId, Movement movement);
}