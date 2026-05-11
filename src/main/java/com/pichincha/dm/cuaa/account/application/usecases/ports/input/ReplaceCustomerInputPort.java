package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Mono;

public interface ReplaceCustomerInputPort {
    Mono<Void> replaceCustomer(CustomerId customerId, Customer customer);
}