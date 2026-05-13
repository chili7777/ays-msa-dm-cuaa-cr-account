package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.time.LocalDateTime;
import reactor.core.publisher.Flux;

public interface GetMovementsByDateRangeOutputPort {
    Flux<Movement> findByAccountIdAndDateRange(AccountId accountId, LocalDateTime start, LocalDateTime end);
}