package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetCustomerByIdInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerFinder implements GetCustomerByIdInputPort {

    private final GetCustomerByIdOutputPort repository;

    @Override
    public Mono<Customer> getCustomerById(CustomerId customerId) {
        return repository.findById(customerId);
    }
}