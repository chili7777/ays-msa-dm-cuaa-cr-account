package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.*;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper.CustomerHttpRequestMapper;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = CustomersController.class)
final class CustomersControllerTest extends RequestTestCase {

    @MockitoBean
    private CreateCustomerInputPort createCustomerUseCase;
    @MockitoBean
    private ListCustomersInputPort listCustomersUseCase;
    @MockitoBean
    private GetCustomerByIdInputPort getCustomerByIdUseCase;
    @MockitoBean
    private ReplaceCustomerInputPort replaceCustomerUseCase;
    @MockitoBean
    private PatchCustomerInputPort patchCustomerUseCase;
    @MockitoBean
    private DeleteCustomerInputPort deleteCustomerUseCase;
    @MockitoBean
    private CustomerHttpRequestMapper customerMapper;

    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Test
    void given_validCustomerRequest_when_postCustomer_then_return201() {
        CustomerCreateRequestDto request = new CustomerCreateRequestDto();
        request.setName("John Doe");
        request.setIdentification("1234567890");
        request.setEmail("john@example.com");
        request.setPhone("0999999999");
        request.setAddress("Street 123");
        request.setStatus(true);
        request.setPassword("password123");

        Customer customer = CustomerMother.random();

        when(customerMapper.toCustomer(any(CustomerCreateRequestDto.class))).thenReturn(customer);
        when(createCustomerUseCase.createCustomer(any(Customer.class))).thenReturn(Mono.empty());

        assertPostResponse("/customers", request, 201);
    }
}