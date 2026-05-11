package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountTypeMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.StatusMother;
import java.util.List;
import org.junit.jupiter.api.Test;

final class InMemoryAccountRepositoryTest extends AccountInfrastructureTestCase {

    @Test
    void given_validAccount_when_save_then_accountIsPersisted() {
        Account account = AccountMother.random();

        repository.save(account).block();
        Account found = repository.findById(account.accountId()).block();

        assertNotNull(found);
        assertEquals(account.accountId(), found.accountId());
    }

    @Test
    void given_savedAccount_when_findById_then_returnAccount() {
        Account account = AccountMother.random();

        repository.save(account).block();
        Account found = repository.findById(account.accountId()).block();

        assertNotNull(found);
        assertTrue(found.accountId().equals(account.accountId()));
    }

    @Test
    void given_nonExistingAccountId_when_findById_then_returnEmpty() {
        AccountId nonExistingAccountId = AccountIdMother.random();

        boolean isPresent = repository.findById(nonExistingAccountId).blockOptional().isPresent();

        assertFalse(isPresent);
    }

    @Test
    void given_multipleAccounts_when_findAll_then_returnAll() {
        Account account1 = AccountMother.random();
        Account account2 = AccountMother.random();

        repository.save(account1).block();
        repository.save(account2).block();

        List<Account> all = repository.findAllAccounts().collectList().block();

        assertNotNull(all);
        assertTrue(all.size() >= 2);
    }

    @Test
    void given_savedAccount_when_update_then_accountIsUpdated() {
        Account account = AccountMother.random();
        repository.save(account).block();

        Account updatedAccount = new Account(
                account.accountId(),
                account.clientId(),
                account.accountNumber(),
                AccountTypeMother.random(),
                account.initialBalance(),
                account.status()
        );

        repository.update(account.accountId(), updatedAccount).block();
        Account found = repository.findById(account.accountId()).block();

        assertNotNull(found);
        assertEquals(updatedAccount.accountType(), found.accountType());
    }

    @Test
    void given_savedAccount_when_patch_then_accountIsPartiallyUpdated() {
        Account account = AccountMother.random();
        repository.save(account).block();

        Account partialAccount = new Account(
                null, null, null,
                AccountTypeMother.random(),
                null,
                StatusMother.random()
        );

        repository.patch(account.accountId(), partialAccount).block();
        Account found = repository.findById(account.accountId()).block();

        assertNotNull(found);
        assertEquals(partialAccount.accountType(), found.accountType());
        assertEquals(partialAccount.status(), found.status());
        assertEquals(account.initialBalance(), found.initialBalance());
    }

    @Test
    void given_savedAccount_when_deactivate_then_accountIsDeactivated() {
        Account account = AccountMother.create(
                AccountIdMother.random(),
                AccountMother.random().clientId(),
                AccountMother.random().accountNumber(),
                AccountTypeMother.random(),
                AccountMother.random().initialBalance(),
                StatusMother.create(true)
        );
        repository.save(account).block();

        repository.deactivate(account.accountId()).block();
        Account found = repository.findById(account.accountId()).block();

        assertNotNull(found);
        assertFalse(found.status().getValue());
    }
}