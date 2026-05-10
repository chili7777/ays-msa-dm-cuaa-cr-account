package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Profile("test")
@RequiredArgsConstructor
public final class InMemoryAccountRepository implements CreateAccountOutputPort {

    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Mono<Void> save(Account account) {
        accounts.put(account.accountNumber(), account);
        return Mono.empty();
    }

    public Mono<Account> findByAccountNumber(String accountNumber) {
        return Mono.justOrEmpty(accounts.get(accountNumber));
    }
}