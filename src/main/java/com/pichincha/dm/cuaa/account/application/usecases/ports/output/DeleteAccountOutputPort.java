package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

public interface DeleteAccountOutputPort {
    Mono<Void> deactivate(AccountId accountId);
}