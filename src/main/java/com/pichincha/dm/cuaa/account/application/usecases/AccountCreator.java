package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByAccountNumberOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountCreator implements CreateAccountInputPort {

    private final CreateAccountOutputPort accountPersistence;
    private final GetAccountByAccountNumberOutputPort getAccountByAccountNumber;
    private final GetCustomerByIdOutputPort getCustomerById;

    @Override
    public Mono<Void> createAccount(Account account) {
        return getCustomerById.findById(account.clientId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El cliente con ID " + account.clientId().getValue() + " no existe")))
                .then(getAccountByAccountNumber.getByAccountNumber(account.accountNumber().getValue()))
                .flatMap(existing -> Mono.<Void>error(new DuplicateResourceException("La cuenta con número " + account.accountNumber().getValue() + " ya existe")))
                .switchIfEmpty(Mono.defer(() -> {
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
                }));
    }
}