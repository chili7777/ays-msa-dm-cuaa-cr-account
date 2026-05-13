package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.shared.objectmothers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
final class MovementCreatorTest {

    @Mock
    private CreateMovementOutputPort movementPersistence;
    @Mock
    private GetAccountByIdOutputPort accountRepository;
    @Mock
    private PatchAccountOutputPort accountUpdatePort;
    @Mock
    private GetSystemParameterValueOutputPort parameterPort;
    @Mock
    private GetDailyWithdrawalSumOutputPort dailySumPort;
    @Mock
    private MovementEventPublisher eventPublisher;

    @InjectMocks
    private MovementCreator movementCreator;

    @Test
    void given_validDeposit_when_createMovement_then_persistMovement() {
        Movement movement = MovementMother.create(null, AccountIdMother.random(), 50.0, "DEPOSIT", null, null);
        Account account = AccountMother.create(
                movement.accountId(),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                new AccountType("CURRENT"),
                new InitialBalance(100.0),
                new Status(true)
        );

        when(accountRepository.findById(movement.accountId())).thenReturn(Mono.just(account));
        when(movementPersistence.save(any(Movement.class))).thenReturn(Mono.empty());
        when(accountUpdatePort.patch(any(), any())).thenReturn(Mono.empty());

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectNextCount(1)
                .verifyComplete();

        verify(movementPersistence, atLeastOnce()).save(any(Movement.class));
        verify(eventPublisher).publish(any(Movement.class));
    }

    @Test
    void given_validWithdrawal_when_createMovement_then_validateLimitAndPersist() {
        Movement movement = MovementMother.create(null, AccountIdMother.random(), 50.0, "WITHDRAWAL", null, null);
        Account account = AccountMother.create(
                movement.accountId(),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                new AccountType("CURRENT"),
                new InitialBalance(1000.0),
                new Status(true)
        );

        when(accountRepository.findById(movement.accountId())).thenReturn(Mono.just(account));
        when(parameterPort.getValueByCode("DAILY_DEBIT_LIMIT")).thenReturn(Mono.just("1000"));
        when(dailySumPort.getSumByAccountIdAndDate(movement.accountId())).thenReturn(Mono.just(0.0));
        when(movementPersistence.save(any(Movement.class))).thenReturn(Mono.empty());
        when(accountUpdatePort.patch(any(), any())).thenReturn(Mono.empty());

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectNextCount(1)
                .verifyComplete();

        verify(dailySumPort).getSumByAccountIdAndDate(movement.accountId());
        verify(movementPersistence).save(argThat(m -> m.amount().getValue() == -50.0));
        verify(eventPublisher).publish(any(Movement.class));
    }

    @Test
    void given_withdrawalExceedingDailyLimit_when_createMovement_then_throwCupoDiarioExcedido() {
        Movement movement = MovementMother.create(null, AccountIdMother.random(), 600.0, "WITHDRAWAL", null, null);
        Account account = AccountMother.create(
                movement.accountId(),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                new AccountType("CURRENT"),
                new InitialBalance(2000.0),
                new Status(true)
        );

        when(accountRepository.findById(movement.accountId())).thenReturn(Mono.just(account));
        when(parameterPort.getValueByCode("DAILY_DEBIT_LIMIT")).thenReturn(Mono.just("1000"));
        when(dailySumPort.getSumByAccountIdAndDate(movement.accountId())).thenReturn(Mono.just(500.0));

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectErrorMessage("Cupo diario Excedido")
                .verify();
    }

    @Test
    void given_withdrawalWithMissingLimitParameter_when_createMovement_then_useDefaultLimit() {
        Movement movement = MovementMother.create(null, AccountIdMother.random(), 1100.0, "WITHDRAWAL", null, null);
        Account account = AccountMother.create(
                movement.accountId(),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                new AccountType("CURRENT"),
                new InitialBalance(2000.0),
                new Status(true)
        );

        when(accountRepository.findById(movement.accountId())).thenReturn(Mono.just(account));
        when(parameterPort.getValueByCode("DAILY_DEBIT_LIMIT")).thenReturn(Mono.empty());
        when(dailySumPort.getSumByAccountIdAndDate(movement.accountId())).thenReturn(Mono.just(0.0));

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectErrorMessage("Cupo diario Excedido")
                .verify();
    }

    @Test
    void given_debitMovementWithAmountGreaterThanBalance_when_createMovement_then_throwSaldoNoDisponible() {
        AccountId accountId = AccountIdMother.random();
        Movement movement = MovementMother.create(
                null, accountId, 100.0, "WITHDRAWAL", null, null
        );
        Account account = AccountMother.create(
                accountId, CustomerIdMother.random(), AccountNumberMother.random(),
                new AccountType("CURRENT"), new InitialBalance(50.0), new Status(true)
        );

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectErrorMessage("Saldo no disponible")
                .verify();
    }

    @Test
    void given_debitMovementWithZeroBalance_when_createMovement_then_throwSaldoNoDisponible() {
        AccountId accountId = AccountIdMother.random();
        Movement movement = MovementMother.create(
                null, accountId, 10.0, "WITHDRAWAL", null, null
        );
        Account account = AccountMother.create(
                accountId, CustomerIdMother.random(), AccountNumberMother.random(),
                new AccountType("CURRENT"), new InitialBalance(0.0), new Status(true)
        );

        when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));

        StepVerifier.create(movementCreator.createMovement(movement))
                .expectErrorMessage("Saldo no disponible")
                .verify();
    }
}