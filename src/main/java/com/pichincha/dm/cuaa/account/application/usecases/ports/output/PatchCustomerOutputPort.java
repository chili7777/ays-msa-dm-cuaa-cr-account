package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Mono;

public interface PatchCustomerOutputPort {
    Mono<Void> patch(CustomerId customerId, Customer customer);
}