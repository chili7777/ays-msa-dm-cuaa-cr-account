package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdentificationOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerReplacer implements ReplaceCustomerInputPort {

    private final ReplaceCustomerOutputPort repository;
    private final GetCustomerByIdentificationOutputPort getCustomerByIdentification;
    private final PasswordHasher passwordHasher;

    @Override
    public Mono<Void> replaceCustomer(CustomerId customerId, Customer customer) {
        Mono<Void> validationMono = Mono.empty();
        if (customer.identification() != null && customer.identification().getValue() != null) {
            validationMono = getCustomerByIdentification.getByIdentification(customer.identification())
                    .flatMap(existing -> {
                        if (!existing.id().getValue().equals(customerId.getValue())) {
                            return Mono.<Void>error(new DuplicateResourceException("El cliente con identificación " + customer.identification().getValue() + " ya existe"));
                        }
                        return Mono.empty();
                    });
        }
        return validationMono.then(Mono.defer(() -> {
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
                }));
    }
}