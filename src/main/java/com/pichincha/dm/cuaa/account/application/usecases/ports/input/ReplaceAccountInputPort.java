package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

public interface ReplaceAccountInputPort {
    Mono<Void> replaceAccount(AccountId accountId, Account account);
}