package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import reactor.core.publisher.Mono;

public interface CreateMovementOutputPort {
    Mono<Void> save(Movement movement);
}