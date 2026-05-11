package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AccountReplacerTest {

    @Mock
    private ReplaceAccountOutputPort accountPersistence;

    @InjectMocks
    private AccountReplacer accountReplacer;

    @Test
    void given_account_when_replaceAccount_then_updateAccount() {
        String accountId = UuidMother.random().toString();
        Account account = AccountMother.random();

        when(accountPersistence.update(accountId, account)).thenReturn(Mono.empty());

        accountReplacer.replaceAccount(accountId, account).block();

        verify(accountPersistence, atLeastOnce()).update(accountId, account);
    }
}