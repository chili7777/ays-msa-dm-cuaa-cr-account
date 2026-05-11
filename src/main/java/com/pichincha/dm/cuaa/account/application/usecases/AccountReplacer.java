package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceAccountOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountReplacer implements ReplaceAccountInputPort {

    private final ReplaceAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> replaceAccount(String accountId, Account account) {
        return accountPersistence.update(accountId, account);
    }
}