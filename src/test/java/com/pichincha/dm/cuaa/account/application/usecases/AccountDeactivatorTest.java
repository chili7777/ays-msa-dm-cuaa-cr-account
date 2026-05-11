package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class AccountDeactivatorTest {

    @Mock
    private DeleteAccountOutputPort accountPersistence;

    @InjectMocks
    private AccountDeactivator accountDeactivator;

    @Test
    void given_existingAccountId_when_deleteAccount_then_deactivateAccount() {
        AccountId accountId = AccountIdMother.random();

        when(accountPersistence.deactivate(accountId)).thenReturn(Mono.empty());

        accountDeactivator.deleteAccount(accountId).block();

        verify(accountPersistence, atLeastOnce()).deactivate(accountId);
    }
}