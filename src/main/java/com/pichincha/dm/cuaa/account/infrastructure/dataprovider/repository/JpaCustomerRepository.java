package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.*;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.AccountEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.ClientEntity;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.AccountJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.CustomerJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.MovementJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.CustomerRepositoryMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaCustomerRepository implements
        CreateCustomerOutputPort, ListCustomersOutputPort, GetCustomerByIdOutputPort, ReplaceCustomerOutputPort, PatchCustomerOutputPort, DeleteCustomerOutputPort, GetCustomerByIdentificationOutputPort {

    private final CustomerJpaRepository customerJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final MovementJpaRepository movementJpaRepository;
    private final CustomerRepositoryMapper customerMapper;

    @Override
    public Mono<Void> save(Customer customer) {
        return Mono.fromCallable(() -> {
            customerJpaRepository.saveAndFlush(customerMapper.toCustomerEntity(customer));
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Flux<Customer> findAllCustomers() {
        return Flux.defer(() -> Flux.fromIterable(customerJpaRepository.findAll()))
                .map(customerMapper::toCustomer)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Customer> findById(CustomerId customerId) {
        return Mono.fromCallable(() -> customerJpaRepository.findById(customerId.getValue()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(customerMapper::toCustomer);
    }

    @Override
    public Mono<Customer> getByIdentification(Identification identification) {
        return Mono.fromCallable(() -> customerJpaRepository.findByIdentification(identification.getValue()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(customerMapper::toCustomer);
    }

    @Override
    public Mono<Void> update(CustomerId customerId, Customer customer) {
        return Mono.fromRunnable(() -> {
            ClientEntity existing = customerJpaRepository.findById(customerId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId.getValue()));

            existing.setIdentification(customer.identification().getValue());
            existing.setFullName(customer.fullName().getValue());
            existing.setGender(customer.gender().getValue());
            existing.setAge(customer.age().getValue());
            existing.setEmail(customer.email().getValue());
            existing.setPhone(customer.phone().getValue());
            existing.setAddress(customer.address().getValue());
            existing.setPassword(customer.password().getValue());
            existing.setStatus(customer.status().getValue());
            if (customer.role() != null) existing.setRole(customer.role().getValue());

            customerJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> patch(CustomerId customerId, Customer customer) {
        return Mono.fromRunnable(() -> {
            ClientEntity existing = customerJpaRepository.findById(customerId.getValue())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId.getValue()));

            if (customer.identification() != null) existing.setIdentification(customer.identification().getValue());
            if (customer.fullName() != null) existing.setFullName(customer.fullName().getValue());
            if (customer.gender() != null) existing.setGender(customer.gender().getValue());
            if (customer.age() != null) existing.setAge(customer.age().getValue());
            if (customer.email() != null) existing.setEmail(customer.email().getValue());
            if (customer.phone() != null) existing.setPhone(customer.phone().getValue());
            if (customer.address() != null) existing.setAddress(customer.address().getValue());
            if (customer.password() != null) existing.setPassword(customer.password().getValue());
            if (customer.status() != null) existing.setStatus(customer.status().getValue());
            if (customer.role() != null) existing.setRole(customer.role().getValue());

            customerJpaRepository.saveAndFlush(existing);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    @Transactional
    public Mono<Void> deactivate(CustomerId customerId) {
        return Mono.fromRunnable(() -> {
            if (customerJpaRepository.existsById(customerId.getValue())) {
                String cid = customerId.getValue();
                java.util.List<AccountEntity> accounts = accountJpaRepository.findByClientId(cid);
                for (AccountEntity account : accounts) {
                    movementJpaRepository.deleteByAccountId(account.getAccountId());
                }
                accountJpaRepository.deleteByClientId(cid);
                customerJpaRepository.deleteById(cid);
            } else {
                throw new ResourceNotFoundException("Customer not found: " + customerId.getValue());
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}