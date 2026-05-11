package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import reactor.core.publisher.Flux;

public interface ListMovementsInputPort {
    Flux<Movement> listMovements();
}