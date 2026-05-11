package com.pichincha.dm.cuaa.account.domain.usecases;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AccountCreatorTest {

    @Mock
    private CreateAccountOutputPort accountPersistence;

    @InjectMocks
    private AccountCreator accountCreator;

    @Test
    void given_validAccountData_when_createAccount_then_persistAccount() {
        Account account = AccountMother.random();

        when(accountPersistence.save(account)).thenReturn(Mono.empty());

        accountCreator.createAccount(account).block();

        verify(accountPersistence, atLeastOnce()).save(account);
    }
}