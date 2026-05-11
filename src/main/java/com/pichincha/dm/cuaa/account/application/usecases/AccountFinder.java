package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.GetAccountByIdInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByIdOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

@UseCaseService
@RequiredArgsConstructor
public class AccountFinder implements GetAccountByIdInputPort {

    private final GetAccountByIdOutputPort accountPersistence;

    @Override
    public Mono<Account> getAccountById(AccountId accountId) {
        return accountPersistence.findById(accountId);
    }
}