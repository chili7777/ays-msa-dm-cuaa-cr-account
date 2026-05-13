package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.AccountJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.MovementJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.MovementRepositoryMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaMovementRepository implements
        CreateMovementOutputPort, ListMovementsOutputPort, GetMovementByIdOutputPort, ReplaceMovementOutputPort, PatchMovementOutputPort, DeleteMovementOutputPort, GetDailyWithdrawalSumOutputPort, GetMovementsByDateRangeOutputPort {

    private final MovementJpaRepository movementJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final MovementRepositoryMapper movementMapper;

    @Override
    public Mono<Void> save(Movement movement) {
        return Mono.fromCallable(() -> {
            if (!accountJpaRepository.existsById(movement.accountId().getValue())) {
                throw new IllegalArgumentException("Account does not exist");
            }
            movementJpaRepository.saveAndFlush(movementMapper.toMovementEntity(movement));
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Flux<Movement> findAllMovements() {
        return Flux.defer(() -> Flux.fromIterable(movementJpaRepository.findAll()))
                .map(movementMapper::toMovement)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Movement> findMovementsByAccountId(AccountId accountId) {
        return Flux.defer(() -> Flux.fromIterable(movementJpaRepository.findByAccountId(accountId.getValue())))
                .map(movementMapper::toMovement)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Movement> findMovementsByCustomerAndAccountId(CustomerId customerId, AccountId accountId) {
        return Mono.fromCallable(() -> accountJpaRepository.findById(accountId.getValue()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .flatMapMany(account -> {
                    if (account.getClientId().equals(customerId.getValue())) {
                        return findMovementsByAccountId(accountId);
                    }
                    return Flux.empty();
                });
    }

    @Override
    public Mono<Movement> findById(MovementId movementId) {
        return Mono.fromCallable(() -> movementJpaRepository.findById(movementId.getValue()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(movementMapper::toMovement);
    }

    @Override
    public Mono<Void> update(MovementId movementId, Movement movement) {
        return Mono.fromRunnable(() -> {
            MovementEntity existing = movementJpaRepository.findById(movementId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Movement not found: " + movementId.getValue()));
            
            existing.setMovementType(movement.movementType().getValue());
            existing.setAmount(movement.amount().getValue());
            if (movement.description() != null) existing.setDescription(movement.description().getValue());
            
            movementJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> patch(MovementId movementId, Movement movement) {
        return Mono.fromRunnable(() -> {
            MovementEntity existing = movementJpaRepository.findById(movementId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Movement not found: " + movementId.getValue()));
            
            if (movement.movementDate() != null) existing.setMovementDate(movement.movementDate().getValue());
            if (movement.movementType() != null) existing.setMovementType(movement.movementType().getValue());
            if (movement.amount() != null) existing.setAmount(movement.amount().getValue());
            if (movement.balance() != null) existing.setBalance(movement.balance().getValue());
            if (movement.status() != null) existing.setStatus(movement.status().getValue());
            if (movement.description() != null) existing.setDescription(movement.description().getValue());
            
            movementJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> deactivate(MovementId movementId) {
        return Mono.fromRunnable(() -> {
            if (movementJpaRepository.existsById(movementId.getValue())) {
                movementJpaRepository.deleteById(movementId.getValue());
            } else {
                throw new ResourceNotFoundException("Movement not found: " + movementId.getValue());
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Double> getSumByAccountIdAndDate(AccountId accountId) {
        return Mono.fromCallable(() -> {
            LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            Double sum = movementJpaRepository.sumAmountByAccountIdAndTypeAndDateBetween(
                    accountId.getValue(), "WITHDRAWAL", startOfDay, endOfDay);
            return sum != null ? Math.abs(sum) : 0.0;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateRange(AccountId accountId, LocalDateTime start, LocalDateTime end) {
        return Flux.defer(() -> Flux.fromIterable(movementJpaRepository.findByAccountIdAndMovementDateBetween(accountId.getValue(), start, end)))
                .map(movementMapper::toMovement)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Movement> findMovementsByFilters(AccountId accountId, LocalDateTime start, LocalDateTime end, String type) {
        return Flux.defer(() -> {
            String accId = accountId != null ? accountId.getValue() : null;
            return Flux.fromIterable(movementJpaRepository.findByFilters(accId, start, end, type));
        })
        .map(movementMapper::toMovement)
        .subscribeOn(Schedulers.boundedElastic());
    }
}