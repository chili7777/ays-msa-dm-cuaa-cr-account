package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class AccountListerTest {

    @Mock
    private ListAccountsOutputPort accountPersistence;

    @InjectMocks
    private AccountLister accountLister;

    @Test
    void given_existingAccounts_when_listAccounts_then_returnAccounts() {
        Account account = AccountMother.random();

        when(accountPersistence.findAll()).thenReturn(Flux.just(account));

        List<Account> accounts = accountLister.listAccounts().collectList().block();

        assert accounts != null;
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }
}