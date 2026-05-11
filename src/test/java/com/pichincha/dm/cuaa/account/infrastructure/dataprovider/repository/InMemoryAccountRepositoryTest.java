package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountNumberMother;
import org.junit.jupiter.api.Test;

final class InMemoryAccountRepositoryTest extends AccountInfrastructureTestCase {

    @Test
    void given_validAccount_when_save_then_accountIsPersisted() {
        Account account = AccountMother.random();

        repository.save(account).block();
        Account found = repository.findByAccountNumber(account.accountNumber().getValue()).block();

        assertNotNull(found);
    }

    @Test
    void given_savedAccount_when_findByAccountNumber_then_returnAccount() {
        Account account = AccountMother.random();

        repository.save(account).block();
        Account found = repository.findByAccountNumber(account.accountNumber().getValue()).block();

        assertNotNull(found);
        assertTrue(found.accountNumber().equals(account.accountNumber()));
    }

    @Test
    void given_nonExistingAccountNumber_when_findByAccountNumber_then_returnEmpty() {
        String nonExistingAccountNumber = AccountNumberMother.create("ACC-0000-NONEXIST-00").getValue();

        boolean isPresent = repository.findByAccountNumber(nonExistingAccountNumber).blockOptional().isPresent();

        assertFalse(isPresent);
    }
}