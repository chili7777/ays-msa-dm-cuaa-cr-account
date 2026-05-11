package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountPatcher implements PatchAccountInputPort {

    private final PatchAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> patchAccount(String accountId, Account partialAccount) {
        return accountPersistence.patch(accountId, partialAccount);
    }
}