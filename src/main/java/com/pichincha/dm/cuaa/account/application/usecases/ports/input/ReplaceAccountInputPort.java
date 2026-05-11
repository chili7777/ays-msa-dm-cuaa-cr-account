package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import reactor.core.publisher.Mono;

public interface ReplaceAccountInputPort {
    Mono<Void> replaceAccount(String accountId, Account account);
}