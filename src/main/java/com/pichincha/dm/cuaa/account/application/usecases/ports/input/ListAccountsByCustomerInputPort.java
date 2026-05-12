package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import reactor.core.publisher.Flux;

public interface ListAccountsByCustomerInputPort {
    Flux<Account> listAccountsByCustomer(CustomerId customerId);
}