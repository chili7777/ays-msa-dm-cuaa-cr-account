package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Flux;

public interface ListAccountsOutputPort {
    Flux<Account> findAllAccounts();
}