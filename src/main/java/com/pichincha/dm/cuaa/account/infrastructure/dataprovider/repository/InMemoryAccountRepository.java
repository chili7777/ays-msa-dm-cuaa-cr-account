package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.CustomerEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.MovementEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapper;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.CustomerRepositoryMapper;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.MovementRepositoryMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Profile({"test", "local", "default", "development", "staging", "production"})
@RequiredArgsConstructor
public final class InMemoryAccountRepository implements
        CreateCustomerOutputPort, ListCustomersOutputPort, GetCustomerByIdOutputPort, ReplaceCustomerOutputPort, PatchCustomerOutputPort, DeleteCustomerOutputPort,
        CreateAccountOutputPort, ListAccountsOutputPort, GetAccountByIdOutputPort, ReplaceAccountOutputPort, PatchAccountOutputPort, DeleteAccountOutputPort,
        CreateMovementOutputPort, ListMovementsOutputPort, GetMovementByIdOutputPort, ReplaceMovementOutputPort, PatchMovementOutputPort, DeleteMovementOutputPort {

    private final CustomerRepositoryMapper customerMapper;
    private final AccountRepositoryMapper accountMapper;
    private final MovementRepositoryMapper movementMapper;

    private final Map<String, CustomerEntity> customers = new HashMap<>();
    private final Map<String, AccountEntity> accounts = new HashMap<>();
    private final Map<String, MovementEntity> movements = new HashMap<>();

    // --- Customer Implementation ---
    @Override
    public Mono<Void> save(Customer customer) {
        return Mono.fromRunnable(() -> {
            CustomerEntity entity = customerMapper.toCustomerEntity(customer);
            customers.put(entity.id(), entity);
        });
    }

    @Override
    public Flux<Customer> findAllCustomers() {
        return Flux.fromIterable(customers.values()).map(customerMapper::toCustomer);
    }

    @Override
    public Mono<Customer> findById(CustomerId customerId) {
        return Mono.justOrEmpty(customers.get(customerId.getValue())).map(customerMapper::toCustomer);
    }

    @Override
    public Mono<Void> update(CustomerId customerId, Customer customer) {
        return Mono.fromRunnable(() -> {
            if (customers.containsKey(customerId.getValue())) {
                CustomerEntity entity = customerMapper.toCustomerEntity(customer);
                customers.put(customerId.getValue(), entity);
            }
        });
    }

    @Override
    public Mono<Void> patch(CustomerId customerId, Customer customer) {
        return Mono.fromRunnable(() -> {
            CustomerEntity existing = customers.get(customerId.getValue());
            if (existing != null) {
                CustomerEntity updated = new CustomerEntity(
                        existing.id(),
                        customer.identification() != null ? customer.identification().getValue() : existing.identification(),
                        customer.fullName() != null ? customer.fullName().getValue() : existing.fullName(),
                        customer.email() != null ? customer.email().getValue() : existing.email(),
                        customer.phone() != null ? customer.phone().getValue() : existing.phone(),
                        customer.address() != null ? customer.address().getValue() : existing.address(),
                        customer.status() != null ? customer.status().getValue() : existing.status()
                );
                customers.put(customerId.getValue(), updated);
            }
        });
    }

    @Override
    public Mono<Void> deactivate(CustomerId customerId) {
        return Mono.fromRunnable(() -> {
            CustomerEntity existing = customers.get(customerId.getValue());
            if (existing != null) {
                CustomerEntity deactivated = new CustomerEntity(
                        existing.id(), existing.identification(), existing.fullName(), existing.email(),
                        existing.phone(), existing.address(), false);
                customers.put(customerId.getValue(), deactivated);

                accounts.values().stream()
                    .filter(a -> a.clientId().equals(customerId.getValue()))
                    .forEach(a -> deactivate(new AccountId(a.accountId())).subscribe());
            }
        });
    }

    // --- Account Implementation ---
    @Override
    public Mono<Void> save(Account account) {
        return Mono.defer(() -> {
            if (!customers.containsKey(account.clientId().getValue())) {
                return Mono.error(new IllegalArgumentException("Customer does not exist"));
            }
            AccountEntity entity = accountMapper.toAccountEntity(account);
            accounts.put(entity.accountId(), entity);
            return Mono.empty();
        });
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return Flux.fromIterable(accounts.values()).map(accountMapper::toAccount);
    }


    @Override
    public Mono<Account> findById(AccountId accountId) {
        return Mono.justOrEmpty(accounts.get(accountId.getValue())).map(accountMapper::toAccount);
    }

    @Override
    public Mono<Void> update(AccountId accountId, Account account) {
        return Mono.fromRunnable(() -> {
            if (accounts.containsKey(accountId.getValue())) {
                AccountEntity entity = accountMapper.toAccountEntity(account);
                accounts.put(accountId.getValue(), entity);
            }
        });
    }

    @Override
    public Mono<Void> patch(AccountId accountId, Account account) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accounts.get(accountId.getValue());
            if (existing != null) {
                AccountEntity updated = new AccountEntity(
                        existing.accountId(),
                        existing.clientId(),
                        existing.accountNumber(),
                        account.accountType() != null ? account.accountType().getValue() : existing.accountType(),
                        existing.initialBalance(),
                        account.status() != null ? account.status().getValue() : existing.status()
                );
                accounts.put(accountId.getValue(), updated);
            }
        });
    }

    @Override
    public Mono<Void> deactivate(AccountId accountId) {
        return Mono.fromRunnable(() -> {
            AccountEntity existing = accounts.get(accountId.getValue());
            if (existing != null) {
                AccountEntity deactivated = new AccountEntity(
                        existing.accountId(), existing.clientId(), existing.accountNumber(),
                        existing.accountType(), existing.initialBalance(), false);
                accounts.put(accountId.getValue(), deactivated);
            }
        });
    }

    // --- Movement Implementation ---
    @Override
    public Mono<Void> save(Movement movement) {
        return Mono.defer(() -> {
            if (!accounts.containsKey(movement.accountId().getValue())) {
                return Mono.error(new IllegalArgumentException("Account does not exist"));
            }
            MovementEntity entity = movementMapper.toMovementEntity(movement);
            movements.put(entity.movementId(), entity);
            return Mono.empty();
        });
    }

    @Override
    public Flux<Movement> findAllMovements() {
        return Flux.fromIterable(movements.values()).map(movementMapper::toMovement);
    }

    @Override
    public Mono<Movement> findById(MovementId movementId) {
        return Mono.justOrEmpty(movements.get(movementId.getValue())).map(movementMapper::toMovement);
    }

    @Override
    public Mono<Void> update(MovementId movementId, Movement movement) {
        return Mono.fromRunnable(() -> {
            if (movements.containsKey(movementId.getValue())) {
                MovementEntity entity = movementMapper.toMovementEntity(movement);
                movements.put(movementId.getValue(), entity);
            }
        });
    }

    @Override
    public Mono<Void> patch(MovementId movementId, Movement movement) {
        return Mono.fromRunnable(() -> {
            MovementEntity existing = movements.get(movementId.getValue());
            if (existing != null) {
                MovementEntity updated = new MovementEntity(
                        existing.movementId(),
                        existing.accountId(),
                        movement.movementDate() != null ? movement.movementDate().getValue() : existing.movementDate(),
                        movement.movementType() != null ? movement.movementType().getValue() : existing.movementType(),
                        movement.amount() != null ? movement.amount().getValue() : existing.amount()
                );
                movements.put(movementId.getValue(), updated);
            }
        });
    }

    @Override
    public Mono<Void> deactivate(MovementId movementId) {
        return Mono.fromRunnable(() -> movements.remove(movementId.getValue()));
    }
}