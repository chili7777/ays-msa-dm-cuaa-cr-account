package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListAccountsOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerIdMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class AccountByCustomerListerTest {

    @Mock
    private ListAccountsOutputPort accountPersistence;

    @InjectMocks
    private AccountByCustomerLister accountByCustomerLister;

    @Test
    void given_customerId_when_listAccountsByCustomer_then_returnAccounts() {
        CustomerId customerId = CustomerIdMother.random();
        Account account = AccountMother.random();

        when(accountPersistence.findAccountsByCustomerId(customerId)).thenReturn(Flux.just(account));

        List<Account> accounts = accountByCustomerLister.listAccountsByCustomer(customerId).collectList().block();

        assert accounts != null;
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }
}