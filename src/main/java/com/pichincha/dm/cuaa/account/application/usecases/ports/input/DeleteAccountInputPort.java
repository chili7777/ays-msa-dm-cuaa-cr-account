package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

public interface DeleteAccountInputPort {
    Mono<Void> deleteAccount(AccountId accountId);
}