package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.*;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.ResourceNotFoundException;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.*;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.AccountHttpRequestMapper;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.CustomerHttpRequestMapper;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.MovementHttpRequestMapper;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class CustomersController implements CustomersApi {

    private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();
    private static final ResponseEntity<Void> NO_CONTENT_RESPONSE = ResponseEntity.noContent().build();

    private final CreateCustomerInputPort createCustomerUseCase;
    private final ListCustomersInputPort listCustomersUseCase;
    private final GetCustomerByIdInputPort getCustomerByIdUseCase;
    private final ReplaceCustomerInputPort replaceCustomerUseCase;
    private final PatchCustomerInputPort patchCustomerUseCase;
    private final DeleteCustomerInputPort deleteCustomerUseCase;
    private final ListAccountsByCustomerInputPort listAccountsByCustomerUseCase;
    private final ListMovementsByCustomerAndAccountInputPort listMovementsByCustomerAndAccountUseCase;
    private final LoginInputPort loginUseCase;
    private final CustomerHttpRequestMapper customerMapper;
    private final AccountHttpRequestMapper accountMapper;
    private final MovementHttpRequestMapper movementMapper;

    @Override
    public Mono<ResponseEntity<Void>> createCustomer(UUID xGuid, String xApp, Mono<CustomerCreateRequestDto> customerCreateRequestDto, ServerWebExchange exchange) {
        return customerCreateRequestDto
                .map(customerMapper::toCustomer)
                .flatMap(createCustomerUseCase::createCustomer)
                .thenReturn(CREATED_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(UUID xGuid, String xApp, UUID customerId, ServerWebExchange exchange) {
        return deleteCustomerUseCase.deleteCustomer(new CustomerId(customerId.toString()))
                .thenReturn(NO_CONTENT_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(UUID xGuid, String xApp, UUID customerId, ServerWebExchange exchange) {
        return getCustomerByIdUseCase.getCustomerById(new CustomerId(customerId.toString()))
                .map(customerMapper::toCustomerDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found: " + customerId)));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerDto>>> listCustomers(UUID xGuid, String xApp, UUID customerId, String identification, Boolean status, ServerWebExchange exchange) {
        Flux<CustomerDto> customersFlux;
        if (customerId != null) {
            customersFlux = getCustomerByIdUseCase.getCustomerById(new CustomerId(customerId.toString()))
                    .map(customerMapper::toCustomerDto)
                    .flux();
        } else {
            customersFlux = listCustomersUseCase.listCustomers()
                    .map(customerMapper::toCustomerDto);
        }

        if (identification != null && !identification.isBlank()) {
            customersFlux = customersFlux.filter(c -> c.getIdentification().equals(identification));
        }

        if (status != null) {
            customersFlux = customersFlux.filter(c -> c.getStatus().equals(status));
        }

        return Mono.just(ResponseEntity.ok(customersFlux));
    }

    @Override
    public Mono<ResponseEntity<Flux<AccountDto>>> listAccountsByCustomer(UUID xGuid, String xApp, UUID customerId, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                listAccountsByCustomerUseCase.listAccountsByCustomer(new CustomerId(customerId.toString()))
                        .map(accountMapper::toAccountDto)));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCustomer(UUID xGuid, String xApp, UUID customerId, Mono<CustomerPatchRequestDto> customerPatchRequestDto, ServerWebExchange exchange) {
        return customerPatchRequestDto
                .map(customerMapper::toCustomer)
                .flatMap(c -> patchCustomerUseCase.patchCustomer(new CustomerId(customerId.toString()), c))
                .thenReturn(NO_CONTENT_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<Void>> replaceCustomer(UUID xGuid, String xApp, UUID customerId, Mono<CustomerUpdateRequestDto> customerUpdateRequestDto, ServerWebExchange exchange) {
        return customerUpdateRequestDto
                .map(customerMapper::toCustomer)
                .flatMap(c -> replaceCustomerUseCase.replaceCustomer(new CustomerId(customerId.toString()), c))
                .thenReturn(NO_CONTENT_RESPONSE);
    }

    @Override
    public Mono<ResponseEntity<Flux<MovementDto>>> listMovementsByCustomerAndAccount(UUID xGuid, String xApp, UUID customerId, UUID accountId, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                listMovementsByCustomerAndAccountUseCase.listMovementsByCustomerAndAccount(
                                new CustomerId(customerId.toString()),
                                new AccountId(accountId.toString()))
                        .map(movementMapper::toMovementDto)));
    }

    @Override
    public Mono<ResponseEntity<LoginResponseDto>> login(UUID xGuid, String xApp, Mono<LoginRequestDto> loginRequestDto, ServerWebExchange exchange) {
        return loginRequestDto
                .flatMap(dto -> loginUseCase.login(
                        new Identification(dto.getIdentification()),
                        new Password(dto.getPassword())
                ))
                .map(customerMapper::toLoginResponseDto)
                .map(ResponseEntity::ok);
    }
}