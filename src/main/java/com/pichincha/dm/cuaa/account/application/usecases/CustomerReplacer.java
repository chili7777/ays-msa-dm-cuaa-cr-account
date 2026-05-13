package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerReplacer implements ReplaceCustomerInputPort {

    private final ReplaceCustomerOutputPort repository;
    private final PasswordHasher passwordHasher;

    @Override
    public Mono<Void> replaceCustomer(CustomerId customerId, Customer customer) {
        Password password = customer.password() != null ? new Password(passwordHasher.hash(customer.password().getValue())) : null;
        Customer customerToUpdate = new Customer(
                customer.id(),
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
        return repository.update(customerId, customerToUpdate);
    }
}