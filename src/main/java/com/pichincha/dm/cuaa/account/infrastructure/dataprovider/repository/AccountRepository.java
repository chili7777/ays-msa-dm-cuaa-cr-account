package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Profile("!test")
public class AccountRepository implements CreateAccountOutputPort {

    @Override
    public Mono<Void> save(Account account) {
        // Placeholder persistence adapter for TDD refactor step.
        return Mono.empty();
    }
}