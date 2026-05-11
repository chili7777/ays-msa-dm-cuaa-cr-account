package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerReplacer implements ReplaceCustomerInputPort {

    private final ReplaceCustomerOutputPort repository;

    @Override
    public Mono<Void> replaceCustomer(CustomerId customerId, Customer customer) {
        return repository.update(customerId, customer);
    }
}