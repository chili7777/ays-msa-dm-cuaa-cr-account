package com.pichincha.dm.cuaa.account.domain.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Mono;

public interface CreateAccountInputPort {

    Mono<Void> createAccount(Account account);
}