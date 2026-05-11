package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListCustomersInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListCustomersOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class CustomerLister implements ListCustomersInputPort {

    private final ListCustomersOutputPort repository;

    @Override
    public Flux<Customer> listCustomers() {
        return repository.findAllCustomers();
    }
}