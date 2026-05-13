package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateMovementOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetAccountByIdOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountNumberMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AmountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.DescriptionMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementDateMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementTypeMother;
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

    @InjectMocks
    private MovementCreator movementCreator;

    @Test
    void given_validMovementData_when_createMovement_then_persistMovement() {
        Movement movement = MovementMother.random();
        // Ensure account has enough balance for the movement
        Account account = AccountMother.create(
                new com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId(movement.accountId().getValue()),
                CustomerIdMother.random(),
                AccountNumberMother.random(),
                new com.pichincha.dm.cuaa.account.domain.entities.valueobjects.AccountType("CURRENT"),
                new com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance(100000.0),
                new com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status(true)
        );

        when(accountRepository.findById(movement.accountId())).thenReturn(Mono.just(account));
        when(movementPersistence.save(any(Movement.class))).thenReturn(Mono.empty());
        when(accountUpdatePort.patch(any(), any())).thenReturn(Mono.empty());

        movementCreator.createMovement(movement).block();

        verify(movementPersistence, atLeastOnce()).save(any(Movement.class));
        verify(accountUpdatePort).patch(any(), any());
    }

    @Test
    void given_movementWithoutId_when_createMovement_then_generateIdAndPersist() {
        Movement movementWithoutId = new Movement(
                null,
                MovementMother.random().accountId(),
                MovementDateMother.random(),
                MovementTypeMother.random(),
                AmountMother.random(),
                null,
                null,
                DescriptionMother.random()
        );
        Account account = AccountMother.withId(movementWithoutId.accountId(), CustomerIdMother.random());

        when(accountRepository.findById(movementWithoutId.accountId())).thenReturn(Mono.just(account));
        when(movementPersistence.save(any(Movement.class))).thenReturn(Mono.empty());
        when(accountUpdatePort.patch(any(), any())).thenReturn(Mono.empty());

        movementCreator.createMovement(movementWithoutId).block();

        verify(movementPersistence).save(argThat(mov -> mov.movementId() != null));
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