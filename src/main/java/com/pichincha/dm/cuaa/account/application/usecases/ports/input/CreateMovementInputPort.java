package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import reactor.core.publisher.Mono;

public interface CreateMovementInputPort {
    Mono<Movement> createMovement(Movement movement);
}