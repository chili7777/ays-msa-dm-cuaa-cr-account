package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.AccountCreator;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByAccountNumberOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.DuplicateResourceException;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
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

    @Mock
    private GetAccountByAccountNumberOutputPort getAccountByAccountNumber;

    @Mock
    private GetCustomerByIdOutputPort getCustomerById;

    @InjectMocks
    private AccountCreator accountCreator;

    @Test
    void given_validAccountData_when_createAccount_then_persistAccount() {
        Account account = AccountMother.random();

        when(getCustomerById.findById(any())).thenReturn(Mono.just(CustomerMother.random()));
        when(getAccountByAccountNumber.getByAccountNumber(any())).thenReturn(Mono.empty());
        when(accountPersistence.save(any(Account.class))).thenReturn(Mono.empty());

        accountCreator.createAccount(account).block();

        verify(accountPersistence, atLeastOnce()).save(any(Account.class));
    }

    @Test
    void given_accountWithoutId_when_createAccount_then_generateIdAndPersist() {
        Account accountWithoutId = AccountMother.randomWithNullId();

        when(getCustomerById.findById(any())).thenReturn(Mono.just(CustomerMother.random()));
        when(getAccountByAccountNumber.getByAccountNumber(any())).thenReturn(Mono.empty());
        when(accountPersistence.save(any(Account.class))).thenReturn(Mono.empty());

        accountCreator.createAccount(accountWithoutId).block();

        verify(accountPersistence).save(argThat(acc -> acc.accountId() != null));
    }

    @Test
    void given_duplicateAccountNumber_when_createAccount_then_throwDuplicateResourceException() {
        Account account = AccountMother.random();

        when(getCustomerById.findById(any())).thenReturn(Mono.just(CustomerMother.random()));
        when(getAccountByAccountNumber.getByAccountNumber(any())).thenReturn(Mono.just(account));

        assertThrows(DuplicateResourceException.class, () -> accountCreator.createAccount(account).block());
    }
}