package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.DeleteCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerDeactivator implements DeleteCustomerInputPort {

    private final DeleteCustomerOutputPort repository;

    @Override
    public Mono<Void> deleteCustomer(CustomerId customerId) {
        return repository.deactivate(customerId);
    }
}