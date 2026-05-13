package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import java.time.LocalDate;
import reactor.core.publisher.Flux;

public interface ListMovementsInputPort {
    Flux<Movement> listMovements(LocalDate fromDate, LocalDate toDate, String movementType);
}