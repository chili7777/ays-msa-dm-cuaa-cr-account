package com.pichincha.dm.cuaa.account.domain.usecases.ports.input;

import java.util.UUID;
import reactor.core.publisher.Mono;

public interface CreateAccountInputPort {

    Mono<Void> createAccount(UUID clientId,
                             String accountNumber,
                             String accountType,
                             Double initialBalance,
                             Boolean status);
}