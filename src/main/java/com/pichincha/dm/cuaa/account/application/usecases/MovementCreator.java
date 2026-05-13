package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.CreateMovementInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Balance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.InitialBalance;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Status;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class MovementCreator implements CreateMovementInputPort {

    private final CreateMovementOutputPort movementRepository;
    private final GetAccountByIdOutputPort accountRepository;
    private final PatchAccountOutputPort accountUpdatePort;
    private final GetSystemParameterValueOutputPort parameterPort;
    private final GetDailyWithdrawalSumOutputPort dailySumPort;

    @Override
    public Mono<Movement> createMovement(Movement movement) {
        return accountRepository.findById(movement.accountId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found: " + movement.accountId().getValue())))
                .flatMap(account -> {
                    double currentBalance = account.initialBalance().getValue();
                    double amount = movement.amount().getValue();

                    if (movement.movementType().getValue().equalsIgnoreCase("WITHDRAWAL")) {
                        if (currentBalance == 0 || amount > currentBalance) {
                            return Mono.error(new IllegalArgumentException("Saldo no disponible"));
                        }

                        return validateDailyLimit(movement)
                                .then(Mono.defer(() -> processMovement(account, movement, currentBalance, amount)));
                    }

                    return processMovement(account, movement, currentBalance, amount);
                });
    }

    private Mono<Void> validateDailyLimit(Movement movement) {
        return parameterPort.getValueByCode("DAILY_DEBIT_LIMIT")
                .map(Double::valueOf)
                .defaultIfEmpty(1000.0)
                .onErrorResume(e -> Mono.just(1000.0))
                .flatMap(limit -> dailySumPort.getSumByAccountIdAndDate(movement.accountId())
                        .flatMap(currentDailySum -> {
                            if (currentDailySum + movement.amount().getValue() > limit) {
                                return Mono.error(new IllegalArgumentException("Cupo diario excedido"));
                            }
                            return Mono.empty();
                        }));
    }

    private Mono<Movement> processMovement(Account account, Movement movement, double currentBalance, double amount) {
        double newBalanceValue = movement.movementType().getValue().equalsIgnoreCase("WITHDRAWAL")
                ? currentBalance - amount
                : currentBalance + amount;

        Movement movementToSave = new Movement(
                movement.movementId() != null ? movement.movementId() : new MovementId(UUID.randomUUID().toString()),
                movement.accountId(),
                movement.movementDate(),
                movement.movementType(),
                movement.amount(),
                new Balance(newBalanceValue),
                new Status(true),
                movement.description()
        );

        Account accountToPatch = new Account(
                null, null, null, null,
                new InitialBalance(newBalanceValue),
                null
        );

        return movementRepository.save(movementToSave)
                .then(accountUpdatePort.patch(account.accountId(), accountToPatch))
                .thenReturn(movementToSave);
    }
}