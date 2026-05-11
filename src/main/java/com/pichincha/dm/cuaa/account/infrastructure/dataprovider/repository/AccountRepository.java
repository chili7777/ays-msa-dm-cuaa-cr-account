package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByIdOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceAccountOutputPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Profile("!test & !local & !default & !development & !staging & !production")
public class AccountRepository implements CreateAccountOutputPort, ListAccountsOutputPort, GetAccountByIdOutputPort, ReplaceAccountOutputPort, PatchAccountOutputPort, DeleteAccountOutputPort {

    @Override
    public Mono<Void> save(Account account) {
        // Placeholder persistence adapter for TDD refactor step.
        return Mono.empty();
    }

    @Override
    public Flux<Account> findAll(String clientId, Boolean status) {
        return Flux.empty();
    }

    @Override
    public Mono<Account> findById(String accountId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> update(String accountId, Account account) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> patch(String accountId, Account account) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> deactivate(String accountId) {
        return Mono.empty();
    }
}