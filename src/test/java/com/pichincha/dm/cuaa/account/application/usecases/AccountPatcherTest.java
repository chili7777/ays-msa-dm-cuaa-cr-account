package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AccountPatcherTest {

    @Mock
    private PatchAccountOutputPort accountPersistence;

    @InjectMocks
    private AccountPatcher accountPatcher;

    @Test
    void given_partialAccount_when_patchAccount_then_patchAccount() {
        AccountId accountId = AccountIdMother.random();
        Account partialAccount = AccountMother.random();

        when(accountPersistence.patch(accountId, partialAccount)).thenReturn(Mono.empty());

        accountPatcher.patchAccount(accountId, partialAccount).block();

        verify(accountPersistence, atLeastOnce()).patch(accountId, partialAccount);
    }
}