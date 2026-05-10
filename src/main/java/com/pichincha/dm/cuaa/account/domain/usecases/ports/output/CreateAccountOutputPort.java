package com.pichincha.dm.cuaa.account.domain.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Mono;

public interface CreateAccountOutputPort {

    Mono<Void> save(Account account);
}