package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Flux;

public interface ListAccountsInputPort {
    Flux<Account> listAccounts();
}