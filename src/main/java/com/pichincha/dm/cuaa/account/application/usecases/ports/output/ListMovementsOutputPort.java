package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import reactor.core.publisher.Flux;

public interface ListMovementsOutputPort {
    Flux<Movement> findAll();
}