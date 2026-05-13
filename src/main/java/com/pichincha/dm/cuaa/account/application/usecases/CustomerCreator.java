package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerCreator implements CreateCustomerInputPort {

    private final CreateCustomerOutputPort repository;
    private final PasswordHasher passwordHasher;

    @Override
    public Mono<Void> createCustomer(Customer customer) {
        Password password = customer.password() != null ? new Password(passwordHasher.hash(customer.password().getValue())) : null;
        Customer customerToSave = new Customer(
                customer.id() == null ? new CustomerId(UUID.randomUUID().toString()) : customer.id(),
                customer.identification(),
                customer.fullName(),
                customer.gender(),
                customer.age(),
                customer.email(),
                customer.phone(),
                customer.address(),
                password,
                customer.status(),
                customer.role()
        );
        return repository.save(customerToSave);
    }
}