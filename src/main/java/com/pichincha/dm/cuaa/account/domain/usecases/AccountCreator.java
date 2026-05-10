package com.pichincha.dm.cuaa.account.domain.usecases;

import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.input.CreateAccountInputPort;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class AccountCreator implements CreateAccountInputPort {

    private final CreateAccountOutputPort accountPersistence;

    @Override
    public Mono<Void> createAccount(UUID clientId,
                                    String accountNumber,
                                    String accountType,
                                    Double initialBalance,
                                    Boolean status) {
        Account account = new Account(clientId, accountNumber, accountType, initialBalance, status);
        return accountPersistence.save(account);
    }
}