package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapper;
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

    private final AccountRepositoryMapper accountRepositoryMapper;
    private final Map<String, AccountEntity> accounts = new HashMap<>();

    @Override
    public Mono<Void> save(Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity accountEntity = accountRepositoryMapper.toAccountEntity(account);
            accounts.put(account.accountNumber(), accountEntity);
        });
    }

    public Mono<Account> findByAccountNumber(String accountNumber) {
        return Mono.justOrEmpty(accounts.get(accountNumber))
                .map(accountRepositoryMapper::toAccount);
    }
}