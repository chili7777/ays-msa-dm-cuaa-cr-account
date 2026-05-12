package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.InMemoryAccountRepository;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.*;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CustomersControllerTest extends RequestTestCase {

    @Autowired
    private CreateCustomerOutputPort createCustomerOutputPort;

    @Autowired
    private InMemoryAccountRepository repository;

    @BeforeEach
    void setUp() {
        repository.clear();
    }

    @Test
    void given_validCustomerCreateRequest_when_createCustomer_then_returnCreatedStatus() throws Exception {
        CustomerCreateRequestDto requestDto = CustomerCreateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/customers", requestBody, 201, HttpHeadersMother.random());
    }

    @Test
    void given_invalidCustomerCreateRequest_when_createCustomer_then_returnBadRequest() throws Exception {
        CustomerCreateRequestDto requestDto = CustomerCreateRequestDtoMother.random();
        requestDto.setIdentification(null); // identification es obligatorio en el spec
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/customers", requestBody, 400, HttpHeadersMother.random());
    }

    @Test
    void given_identificationAndStatus_when_listCustomers_then_returnOkStatus() throws Exception {
        String identification = "1234567890";
        assertRequest("GET", "/customers?identification=" + identification + "&status=true", 200, HttpHeadersMother.random());
    }

    @Test
    void given_customerId_when_getCustomerById_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        createCustomerOutputPort.save(CustomerMother.withId(new CustomerId(customerId))).block();

        assertRequest("GET", "/customers/" + customerId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_nonExistentCustomerId_when_getCustomerById_then_returnNotFound() throws Exception {
        String customerId = UuidMother.random().toString();
        assertRequest("GET", "/customers/" + customerId, 404, HttpHeadersMother.random());
    }

    @Test
    void given_validCustomerUpdateRequest_when_replaceCustomer_then_returnNoContentStatusAndPreserveId() throws Exception {
        String customerId = UuidMother.random().toString();
        createCustomerOutputPort.save(CustomerMother.withId(new CustomerId(customerId))).block();

        CustomerUpdateRequestDto requestDto = CustomerUpdateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PUT", "/customers/" + customerId, requestBody, 204, HttpHeadersMother.random());

        // Verificar que el ID se preservó en el repositorio
        var updatedCustomer = repository.findById(new CustomerId(customerId)).block();
        org.junit.jupiter.api.Assertions.assertNotNull(updatedCustomer);
        org.junit.jupiter.api.Assertions.assertEquals(customerId, updatedCustomer.id().getValue());
    }

    @Test
    void given_validCustomerPatchRequest_when_patchCustomer_then_returnNoContentStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        createCustomerOutputPort.save(CustomerMother.withId(new CustomerId(customerId))).block();

        CustomerPatchRequestDto requestDto = CustomerPatchRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PATCH", "/customers/" + customerId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_customerId_when_deleteCustomer_then_returnNoContentStatusAndRemoveFromRepository() throws Exception {
        String customerId = UuidMother.random().toString();
        createCustomerOutputPort.save(CustomerMother.withId(new CustomerId(customerId))).block();

        assertRequest("DELETE", "/customers/" + customerId, 204, HttpHeadersMother.random());

        // Verificar que el cliente ya no existe en el repositorio (borrado físico)
        var deletedCustomer = repository.findById(new CustomerId(customerId)).block();
        org.junit.jupiter.api.Assertions.assertNull(deletedCustomer);
    }

    @Test
    void given_customerId_when_listAccountsByCustomer_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        assertRequest("GET", "/customers/" + customerId + "/accounts", 200, HttpHeadersMother.random());
    }

    @Test
    void given_customerIdAndAccountId_when_listMovementsByCustomerAndAccount_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        String accountId = UuidMother.random().toString();
        assertRequest("GET", "/customers/" + customerId + "/accounts/" + accountId + "/movements", 200, HttpHeadersMother.random());
    }

    @Test
    void given_nonExistentCustomerId_when_deleteCustomer_then_returnNotFound() throws Exception {
        String customerId = UuidMother.random().toString();
        assertRequest("DELETE", "/customers/" + customerId, 404, HttpHeadersMother.random());
    }
}