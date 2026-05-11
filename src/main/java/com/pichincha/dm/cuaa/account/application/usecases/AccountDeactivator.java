package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.DeleteAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteAccountOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountDeactivator implements DeleteAccountInputPort {

    private final DeleteAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountPersistence.deactivate(accountId);
    }
}