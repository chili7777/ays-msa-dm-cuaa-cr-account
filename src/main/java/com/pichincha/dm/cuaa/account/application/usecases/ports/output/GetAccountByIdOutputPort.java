package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

public interface GetAccountByIdOutputPort {
    Mono<Account> findById(AccountId accountId);
}