package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.*;
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

    public void clear() {
        customers.clear();
        accounts.clear();
        movements.clear();
    }

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
        return Mono.defer(() -> {
            if (customers.containsKey(customerId.getValue())) {
                CustomerEntity entity = customerMapper.toCustomerEntity(customer);
                CustomerEntity updated = new CustomerEntity(
                        customerId.getValue(),
                        entity.identification(),
                        entity.fullName(),
                        entity.gender(),
                        entity.age(),
                        entity.email(),
                        entity.phone(),
                        entity.address(),
                        entity.password(),
                        entity.status()
                );
                customers.put(customerId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Customer not found: " + customerId.getValue()));
        });
    }

    @Override
    public Mono<Void> patch(CustomerId customerId, Customer customer) {
        return Mono.defer(() -> {
            CustomerEntity existing = customers.get(customerId.getValue());
            if (existing != null) {
                CustomerEntity updated = new CustomerEntity(
                        existing.id(),
                        customer.identification() != null ? customer.identification().getValue() : existing.identification(),
                        customer.fullName() != null ? customer.fullName().getValue() : existing.fullName(),
                        customer.gender() != null ? customer.gender().getValue() : existing.gender(),
                        customer.age() != null ? customer.age().getValue() : existing.age(),
                        customer.email() != null ? customer.email().getValue() : existing.email(),
                        customer.phone() != null ? customer.phone().getValue() : existing.phone(),
                        customer.address() != null ? customer.address().getValue() : existing.address(),
                        customer.password() != null ? customer.password().getValue() : existing.password(),
                        customer.status() != null ? customer.status().getValue() : existing.status()
                );
                customers.put(customerId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Customer not found: " + customerId.getValue()));
        });
    }

    @Override
    public Mono<Void> deactivate(CustomerId customerId) {
        return Mono.defer(() -> {
            if (customers.containsKey(customerId.getValue())) {
                String cid = customerId.getValue();
                // Identificar cuentas a borrar para limpiar sus movimientos
                java.util.List<String> accountIds = accounts.values().stream()
                        .filter(a -> a.clientId().equals(cid))
                        .map(AccountEntity::accountId)
                        .toList();

                // Borrar movimientos asociados a las cuentas del cliente
                movements.values().removeIf(m -> accountIds.contains(m.accountId()));

                // Borrar las cuentas del cliente
                accounts.values().removeIf(a -> a.clientId().equals(cid));

                // Borrar el cliente
                customers.remove(cid);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Customer not found: " + customerId.getValue()));
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
    public Flux<Account> findAccountsByCustomerId(CustomerId customerId) {
        return Flux.fromIterable(accounts.values())
                .filter(account -> account.clientId().equals(customerId.getValue()))
                .map(accountMapper::toAccount);
    }


    @Override
    public Mono<Account> findById(AccountId accountId) {
        return Mono.justOrEmpty(accounts.get(accountId.getValue())).map(accountMapper::toAccount);
    }

    @Override
    public Mono<Void> update(AccountId accountId, Account account) {
        return Mono.defer(() -> {
            AccountEntity existing = accounts.get(accountId.getValue());
            if (existing != null) {
                AccountEntity entity = accountMapper.toAccountEntity(account);
                AccountEntity updated = new AccountEntity(
                        accountId.getValue(),
                        existing.clientId(),
                        existing.accountNumber(),
                        entity.accountType(),
                        existing.initialBalance(),
                        entity.status()
                );
                accounts.put(accountId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Account not found: " + accountId.getValue()));
        });
    }

    @Override
    public Mono<Void> patch(AccountId accountId, Account account) {
        return Mono.defer(() -> {
            AccountEntity existing = accounts.get(accountId.getValue());
            if (existing != null) {
                AccountEntity updated = new AccountEntity(
                        existing.accountId(),
                        existing.clientId(),
                        existing.accountNumber(),
                        account.accountType() != null ? account.accountType().getValue() : existing.accountType(),
                        account.initialBalance() != null ? account.initialBalance().getValue() : existing.initialBalance(),
                        account.status() != null ? account.status().getValue() : existing.status()
                );
                accounts.put(accountId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Account not found: " + accountId.getValue()));
        });
    }

    @Override
    public Mono<Void> deactivate(AccountId accountId) {
        return Mono.defer(() -> {
            if (accounts.containsKey(accountId.getValue())) {
                String aid = accountId.getValue();
                // Borrar movimientos asociados a la cuenta
                movements.values().removeIf(m -> m.accountId().equals(aid));
                // Borrar la cuenta
                accounts.remove(aid);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Account not found: " + accountId.getValue()));
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
    public Flux<Movement> findMovementsByAccountId(AccountId accountId) {
        return Flux.fromIterable(movements.values())
                .filter(movement -> movement.accountId().equals(accountId.getValue()))
                .map(movementMapper::toMovement);
    }

    @Override
    public Mono<Movement> findById(MovementId movementId) {
        return Mono.justOrEmpty(movements.get(movementId.getValue())).map(movementMapper::toMovement);
    }

    @Override
    public Mono<Void> update(MovementId movementId, Movement movement) {
        return Mono.defer(() -> {
            MovementEntity existing = movements.get(movementId.getValue());
            if (existing != null) {
                MovementEntity updated = new MovementEntity(
                        movementId.getValue(),
                        existing.accountId(),
                        movement.movementDate() != null ? movement.movementDate().getValue() : existing.movementDate(),
                        movement.movementType() != null ? movement.movementType().getValue() : existing.movementType(),
                        movement.amount() != null ? movement.amount().getValue() : existing.amount(),
                        movement.balance() != null ? movement.balance().getValue() : existing.balance(),
                        movement.status() != null ? movement.status().getValue() : existing.status()
                );
                movements.put(movementId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Movement not found: " + movementId.getValue()));
        });
    }

    @Override
    public Mono<Void> patch(MovementId movementId, Movement movement) {
        return Mono.defer(() -> {
            MovementEntity existing = movements.get(movementId.getValue());
            if (existing != null) {
                MovementEntity updated = new MovementEntity(
                        existing.movementId(),
                        existing.accountId(),
                        movement.movementDate() != null ? movement.movementDate().getValue() : existing.movementDate(),
                        movement.movementType() != null ? movement.movementType().getValue() : existing.movementType(),
                        movement.amount() != null ? movement.amount().getValue() : existing.amount(),
                        movement.balance() != null ? movement.balance().getValue() : existing.balance(),
                        movement.status() != null ? movement.status().getValue() : existing.status()
                );
                movements.put(movementId.getValue(), updated);
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Movement not found: " + movementId.getValue()));
        });
    }

    @Override
    public Mono<Void> deactivate(MovementId movementId) {
        return Mono.defer(() -> {
            if (movements.containsKey(movementId.getValue())) {
                movements.remove(movementId.getValue());
                return Mono.empty();
            }
            return Mono.error(new ResourceNotFoundException("Movement not found: " + movementId.getValue()));
        });
    }
}