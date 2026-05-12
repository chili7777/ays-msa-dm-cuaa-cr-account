package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListAccountsByCustomerInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class AccountByCustomerLister implements ListAccountsByCustomerInputPort {

    private final ListAccountsOutputPort accountPersistence;

    @Override
    public Flux<Account> listAccountsByCustomer(CustomerId customerId) {
        return accountPersistence.findAccountsByCustomerId(customerId);
    }
}