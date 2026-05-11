package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;

@ExtendWith(MockitoExtension.class)
final class AccountFinderTest {

    @Mock
    private GetAccountByIdOutputPort accountPersistence;

    @InjectMocks
    private AccountFinder accountFinder;

    @Test
    void given_existingAccountId_when_getAccountById_then_returnAccount() {
        AccountId accountId = new AccountId(UuidMother.random());
        Account account = AccountMother.random();

        when(accountPersistence.findById(accountId)).thenReturn(Mono.just(account));

        Account found = accountFinder.getAccountById(accountId).block();

        assertEquals(account, found);
    }
}