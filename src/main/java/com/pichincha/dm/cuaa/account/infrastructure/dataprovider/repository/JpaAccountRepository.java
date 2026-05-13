package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.*;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.AccountJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.CustomerJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.MovementJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapper;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.CustomerRepositoryMapper;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.MovementRepositoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JpaAccountRepository implements
        CreateAccountOutputPort, ListAccountsOutputPort, GetAccountByIdOutputPort, ReplaceAccountOutputPort, PatchAccountOutputPort, DeleteAccountOutputPort {

    private final CustomerJpaRepository customerJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final MovementJpaRepository movementJpaRepository;

    private final AccountRepositoryMapper accountMapper;

    // --- Account Implementation ---
    @Override
    public Mono<Void> save(Account account) {
        return Mono.fromCallable(() -> {
            if (!customerJpaRepository.existsById(account.clientId().getValue())) {
                throw new IllegalArgumentException("Customer does not exist");
            }
            accountJpaRepository.saveAndFlush(accountMapper.toAccountEntity(account));
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return Flux.defer(() -> Flux.fromIterable(accountJpaRepository.findAll()))
                .map(accountMapper::toAccount)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Account> findAccountsByCustomerId(CustomerId customerId) {
        return Flux.defer(() -> Flux.fromIterable(accountJpaRepository.findByClientId(customerId.getValue())))
                .map(accountMapper::toAccount)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Account> findById(AccountId accountId) {
        return Mono.fromCallable(() -> accountJpaRepository.findById(accountId.getValue()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(accountMapper::toAccount);
    }

    @Override
    public Mono<Void> update(AccountId accountId, Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accountJpaRepository.findById(accountId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId.getValue()));

            existing.setAccountType(account.accountType().getValue());
            existing.setStatus(account.status().getValue());

            accountJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> patch(AccountId accountId, Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accountJpaRepository.findById(accountId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId.getValue()));

            if (account.accountType() != null) existing.setAccountType(account.accountType().getValue());
            if (account.initialBalance() != null) existing.setInitialBalance(account.initialBalance().getValue());
            if (account.status() != null) existing.setStatus(account.status().getValue());

            accountJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    @Transactional
    public Mono<Void> deactivate(AccountId accountId) {
        return Mono.fromRunnable(() -> {
            if (accountJpaRepository.existsById(accountId.getValue())) {
                movementJpaRepository.deleteByAccountId(accountId.getValue());
                accountJpaRepository.deleteById(accountId.getValue());
            } else {
                throw new ResourceNotFoundException("Account not found: " + accountId.getValue());
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}