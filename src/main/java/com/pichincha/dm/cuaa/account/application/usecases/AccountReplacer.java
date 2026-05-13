package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByAccountNumberOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.ReplaceAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

@UseCaseService
@RequiredArgsConstructor
public class AccountReplacer implements ReplaceAccountInputPort {

    private final ReplaceAccountOutputPort accountPersistence;
    private final GetAccountByAccountNumberOutputPort getAccountByAccountNumber;

    @Override
    public Mono<Void> replaceAccount(AccountId accountId, Account account) {
        if (account.accountNumber() == null || account.accountNumber().getValue() == null) {
            return accountPersistence.update(accountId, account);
        }
        return getAccountByAccountNumber.getByAccountNumber(account.accountNumber().getValue())
                .flatMap(existing -> {
                    if (!existing.accountId().getValue().equals(accountId.getValue())) {
                        return Mono.<Void>error(new DuplicateResourceException("La cuenta con número " + account.accountNumber().getValue() + " ya existe"));
                    }
                    return Mono.empty();
                })
                .then(Mono.defer(() -> accountPersistence.update(accountId, account)));
    }
}