package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import reactor.core.publisher.Mono;

public interface DeleteAccountOutputPort {
    Mono<Void> deactivate(String accountId);
}