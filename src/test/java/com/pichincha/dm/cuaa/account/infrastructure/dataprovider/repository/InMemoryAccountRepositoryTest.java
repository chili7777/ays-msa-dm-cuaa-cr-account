package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.ClientId;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapperImpl;
import org.junit.jupiter.api.Test;

final class InMemoryAccountRepositoryTest {
    private static final ClientId CLIENT_ID = new ClientId("d7f1f2f8-3a64-4305-a7ce-d9f06174bcb5");
    private static final String ACCOUNT_NUMBER = "ACC-999-ALPHA-42";
    private static final String ACCOUNT_TYPE = "SAVINGS";
    private static final Double INITIAL_BALANCE = 157.89;
    private static final Boolean STATUS = true;
    @Test
    void given_validAccount_when_save_then_accountIsPersisted() {
        InMemoryAccountRepository repository = new InMemoryAccountRepository(new AccountRepositoryMapperImpl());
        Account account = new Account(CLIENT_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, INITIAL_BALANCE, STATUS);
        repository.save(account).block();
        Account found = repository.findByAccountNumber(ACCOUNT_NUMBER).block();
        assertNotNull(found);
    }
    @Test
    void given_savedAccount_when_findByAccountNumber_then_returnAccount() {
        InMemoryAccountRepository repository = new InMemoryAccountRepository(new AccountRepositoryMapperImpl());
        Account account = new Account(CLIENT_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, INITIAL_BALANCE, STATUS);
        repository.save(account).block();
        Account found = repository.findByAccountNumber(ACCOUNT_NUMBER).block();
        assertNotNull(found);
        assertTrue(found.accountNumber().equals(ACCOUNT_NUMBER));
    }
    @Test
    void given_nonExistingAccountNumber_when_findByAccountNumber_then_returnEmpty() {
        InMemoryAccountRepository repository = new InMemoryAccountRepository(new AccountRepositoryMapperImpl());
        boolean isPresent = repository.findByAccountNumber("NON-EXISTING-ACC").blockOptional().isPresent();
        assertFalse(isPresent);
    }
}