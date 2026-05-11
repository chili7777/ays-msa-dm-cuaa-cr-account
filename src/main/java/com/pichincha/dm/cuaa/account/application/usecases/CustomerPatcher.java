package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerPatcher implements PatchCustomerInputPort {

    private final PatchCustomerOutputPort repository;

    @Override
    public Mono<Void> patchCustomer(CustomerId customerId, Customer customer) {
        return repository.patch(customerId, customer);
    }
}