package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import reactor.core.publisher.Mono;

public interface DeleteAccountInputPort {
    Mono<Void> deleteAccount(String accountId);
}