package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountCreator implements CreateAccountInputPort {

    private final CreateAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> createAccount(Account account) {
        Account accountToSave = account.accountId() == null
                ? new Account(
                        new AccountId(UUID.randomUUID().toString()),
                        account.clientId(),
                        account.accountNumber(),
                        account.accountType(),
                        account.initialBalance(),
                        account.status()
                )
                : account;
        return accountPersistence.save(accountToSave);
    }
}