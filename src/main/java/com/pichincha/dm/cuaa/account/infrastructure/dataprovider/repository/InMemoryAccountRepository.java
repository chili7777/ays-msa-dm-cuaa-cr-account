package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByIdOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceAccountOutputPort;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Profile({"test", "local", "default", "development"})
@RequiredArgsConstructor
public final class InMemoryAccountRepository implements CreateAccountOutputPort, ListAccountsOutputPort, GetAccountByIdOutputPort, ReplaceAccountOutputPort, PatchAccountOutputPort, DeleteAccountOutputPort {

    private final AccountRepositoryMapper accountRepositoryMapper;
    private final Map<String, AccountEntity> accounts = new HashMap<>();

    @Override
    public Mono<Void> save(Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity accountEntity = accountRepositoryMapper.toAccountEntity(account);
            accounts.put(accountEntity.accountId(), accountEntity);
        });
    }

    @Override
    public Flux<Account> findAll(String clientId, Boolean status) {
        return Flux.fromIterable(accounts.values())
                .filter(entity -> clientId == null || entity.clientId().equals(clientId))
                .filter(entity -> status == null || entity.status().equals(status))
                .map(accountRepositoryMapper::toAccount);
    }

    @Override
    public Mono<Account> findById(String accountId) {
        return Mono.justOrEmpty(accounts.get(accountId))
                .map(accountRepositoryMapper::toAccount);
    }

    @Override
    public Mono<Void> update(String accountId, Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accounts.get(accountId);
            if (existing != null) {
                AccountEntity updated = new AccountEntity(
                        existing.accountId(),
                        account.clientId() != null ? account.clientId().getValue() : existing.clientId(),
                        account.accountNumber() != null ? account.accountNumber().getValue() : existing.accountNumber(),
                        account.accountType() != null ? account.accountType().getValue() : existing.accountType(),
                        account.initialBalance() != null ? account.initialBalance().getValue() : existing.initialBalance(),
                        account.status() != null ? account.status().getValue() : existing.status()
                );
                accounts.put(accountId, updated);
            }
        });
    }

    @Override
    public Mono<Void> patch(String accountId, Account partialAccount) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accounts.get(accountId);
            if (existing != null) {
                AccountEntity updated = new AccountEntity(
                        existing.accountId(),
                        existing.clientId(),
                        existing.accountNumber(),
                        partialAccount.accountType() != null ? partialAccount.accountType().getValue() : existing.accountType(),
                        existing.initialBalance(),
                        partialAccount.status() != null ? partialAccount.status().getValue() : existing.status()
                );
                accounts.put(accountId, updated);
            }
        });
    }

    @Override
    public Mono<Void> deactivate(String accountId) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accounts.get(accountId);
            if (existing != null) {
                AccountEntity deactivated = new AccountEntity(
                        existing.accountId(),
                        existing.clientId(),
                        existing.accountNumber(),
                        existing.accountType(),
                        existing.initialBalance(),
                        false
                );
                accounts.put(accountId, deactivated);
            }
        });
    }
}