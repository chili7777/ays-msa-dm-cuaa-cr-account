package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ListAccountsInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCaseService
@RequiredArgsConstructor
public class AccountLister implements ListAccountsInputPort {

    private final ListAccountsOutputPort accountPersistence;

    @Override
    public Flux<Account> listAccounts() {
        return accountPersistence.findAllAccounts();
    }
}