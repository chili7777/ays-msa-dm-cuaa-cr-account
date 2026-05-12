package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Flux;

public interface ListAccountsOutputPort {
    Flux<Account> findAllAccounts();
    Flux<Account> findAccountsByCustomerId(CustomerId customerId);
}