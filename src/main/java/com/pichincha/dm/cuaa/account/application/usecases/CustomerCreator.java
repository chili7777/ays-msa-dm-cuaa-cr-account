package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerCreator implements CreateCustomerInputPort {

    private final CreateCustomerOutputPort repository;

    @Override
    public Mono<Void> createCustomer(Customer customer) {
        Customer customerToSave = customer.id() == null
                ? new Customer(new CustomerId(UUID.randomUUID().toString()),
                               customer.identification(),
                               customer.fullName(),
                               customer.email(),
                               customer.phone(),
                               customer.address(),
                               customer.status())
                : customer;
        return repository.save(customerToSave);
    }
}