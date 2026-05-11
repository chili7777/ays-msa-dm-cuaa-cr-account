package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Mono;

public interface ReplaceCustomerOutputPort {
    Mono<Void> update(CustomerId customerId, Customer customer);
}