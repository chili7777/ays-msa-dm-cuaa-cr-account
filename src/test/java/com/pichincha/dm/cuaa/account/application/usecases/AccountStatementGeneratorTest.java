package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AccountStatementGeneratorTest {

    @Mock
    private GetCustomerByIdOutputPort customerPort;
    @Mock
    private ListAccountsOutputPort accountsPort;
    @Mock
    private GetMovementsByDateRangeOutputPort movementsPort;
    @Mock
    private GeneratePdfOutputPort pdfPort;

    @InjectMocks
    private AccountStatementGenerator generator;

    @Test
    void given_validRequest_when_getReport_then_returnCorrectTotals() {
        UUID customerId = UUID.randomUUID();
        CustomerId cid = new CustomerId(customerId.toString());
        LocalDate start = LocalDate.now().minusDays(10);
        LocalDate end = LocalDate.now();

        Customer customer = CustomerMother.random();
        Account account = AccountMother.create(
                AccountIdMother.random(), cid, AccountNumberMother.random(),
                null, null, null
        );
        
        Movement debit = MovementMother.create(null, account.accountId(), 100.0, "WITHDRAWAL", 900.0, true);
        Movement credit = MovementMother.create(null, account.accountId(), 200.0, "DEPOSIT", 1100.0, true);

        when(customerPort.findById(cid)).thenReturn(Mono.just(customer));
        when(accountsPort.findAccountsByCustomerId(cid)).thenReturn(Flux.just(account));
        when(movementsPort.findByAccountIdAndDateRange(any(), any(), any()))
                .thenReturn(Flux.just(debit, credit));

        StepVerifier.create(generator.getReport(customerId, start, end))
                .assertNext(report -> {
                    assert report.getClient().equals(customer);
                    assert report.getTotalGeneralDebits() == 100.0;
                    assert report.getTotalGeneralCredits() == 200.0;
                    assert report.getAccounts().size() == 1;
                    assert report.getAccounts().get(0).getMovements().size() == 2;
                })
                .verifyComplete();
    }
}