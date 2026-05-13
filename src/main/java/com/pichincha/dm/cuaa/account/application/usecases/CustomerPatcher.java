package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchCustomerOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerPatcher implements PatchCustomerInputPort {

    private final PatchCustomerOutputPort repository;
    private final PasswordHasher passwordHasher;

    @Override
    public Mono<Void> patchCustomer(CustomerId customerId, Customer customer) {
        Customer customerToPatch = customer.password() != null
                ? new Customer(
                        customer.id(),
                        customer.identification(),
                        customer.fullName(),
                        customer.gender(),
                        customer.age(),
                        customer.email(),
                        customer.phone(),
                        customer.address(),
                        new Password(passwordHasher.hash(customer.password().getValue())),
                        customer.status(),
                        customer.role())
                : customer;

        return repository.patch(customerId, customerToPatch);
    }
}