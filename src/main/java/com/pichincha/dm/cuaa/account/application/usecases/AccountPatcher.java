package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByAccountNumberOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.PatchAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

@UseCaseService
@RequiredArgsConstructor
public class AccountPatcher implements PatchAccountInputPort {

    private final PatchAccountOutputPort accountPersistence;
    private final GetAccountByAccountNumberOutputPort getAccountByAccountNumber;

    @Override
    public Mono<Void> patchAccount(AccountId accountId, Account partialAccount) {
        Mono<Void> validationMono = Mono.empty();
        if (partialAccount.accountNumber() != null && partialAccount.accountNumber().getValue() != null) {
            validationMono = getAccountByAccountNumber.getByAccountNumber(partialAccount.accountNumber().getValue())
                    .flatMap(existing -> {
                        if (!existing.accountId().getValue().equals(accountId.getValue())) {
                            return Mono.error(new DuplicateResourceException("La cuenta con número " + partialAccount.accountNumber().getValue() + " ya existe"));
                        }
                        return Mono.empty();
                    });
        }
        return validationMono.then(Mono.defer(() -> accountPersistence.patch(accountId, partialAccount)));
    }
}