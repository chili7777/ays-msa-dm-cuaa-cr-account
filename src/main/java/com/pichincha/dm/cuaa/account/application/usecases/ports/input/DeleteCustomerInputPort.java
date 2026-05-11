package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Mono;

public interface DeleteCustomerInputPort {
    Mono<Void> deleteCustomer(CustomerId customerId);
}