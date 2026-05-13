package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.time.LocalDate;
import reactor.core.publisher.Flux;

public interface ListMovementsByCustomerAndAccountInputPort {
  Flux<Movement> listMovementsByCustomerAndAccount(CustomerId customerId, AccountId accountId, LocalDate fromDate, LocalDate toDate, String movementType);
}