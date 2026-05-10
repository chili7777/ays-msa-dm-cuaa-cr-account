package com.pichincha.dm.cuaa.account.domain.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountCreator implements CreateAccountInputPort {

    private final CreateAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> createAccount(Account account) {
        return accountPersistence.save(account);
    }
}