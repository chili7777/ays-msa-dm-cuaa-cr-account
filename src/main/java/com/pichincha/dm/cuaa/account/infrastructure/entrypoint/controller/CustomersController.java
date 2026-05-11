package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.*;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.*;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.CustomerHttpRequestMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomersController implements CustomersApi {

    private static final ResponseEntity<Void> CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build();
    private static final ResponseEntity<Void> NO_CONTENT_RESPONSE = ResponseEntity.noContent().build();

    private final CreateCustomerInputPort createCustomerUseCase;
    private final ListCustomersInputPort listCustomersUseCase;
    private final GetCustomerByIdInputPort getCustomerByIdUseCase;
    private final ReplaceCustomerInputPort replaceCustomerUseCase;
    private final PatchCustomerInputPort patchCustomerUseCase;
    private final DeleteCustomerInputPort deleteCustomerUseCase;
    private final CustomerHttpRequestMapper customerMapper;

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
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerDto>>> listCustomers(UUID xGuid, String xApp, String identification, Boolean status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                listCustomersUseCase.listCustomers()
                        .map(customerMapper::toCustomerDto)));
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> patchCustomer(UUID xGuid, String xApp, UUID customerId, Mono<CustomerPatchRequestDto> customerPatchRequestDto, ServerWebExchange exchange) {
        return customerPatchRequestDto
                .map(customerMapper::toCustomer)
                .flatMap(c -> patchCustomerUseCase.patchCustomer(new CustomerId(customerId.toString()), c))
                .then(getCustomerByIdUseCase.getCustomerById(new CustomerId(customerId.toString())))
                .map(customerMapper::toCustomerDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> replaceCustomer(UUID xGuid, String xApp, UUID customerId, Mono<CustomerUpdateRequestDto> customerUpdateRequestDto, ServerWebExchange exchange) {
        return customerUpdateRequestDto
                .map(customerMapper::toCustomer)
                .flatMap(c -> replaceCustomerUseCase.replaceCustomer(new CustomerId(customerId.toString()), c))
                .then(getCustomerByIdUseCase.getCustomerById(new CustomerId(customerId.toString())))
                .map(customerMapper::toCustomerDto)
                .map(ResponseEntity::ok);
    }
}