package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerCreateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerPatchRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerUpdateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.HttpHeadersMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;

class CustomersControllerTest extends RequestTestCase {

    @Test
    void given_validCustomerCreateRequest_when_createCustomer_then_returnCreatedStatus() throws Exception {
        CustomerCreateRequestDto requestDto = CustomerCreateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/customers", requestBody, 201, HttpHeadersMother.random());
    }

    @Test
    void given_identificationAndStatus_when_listCustomers_then_returnOkStatus() throws Exception {
        String identification = "1234567890";
        assertRequest("GET", "/customers?identification=" + identification + "&status=true", 200, HttpHeadersMother.random());
    }

    @Test
    void given_customerId_when_getCustomerById_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        assertRequest("GET", "/customers/" + customerId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_validCustomerUpdateRequest_when_replaceCustomer_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        CustomerUpdateRequestDto requestDto = CustomerUpdateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PUT", "/customers/" + customerId, requestBody, 200, HttpHeadersMother.random());
    }

    @Test
    void given_validCustomerPatchRequest_when_patchCustomer_then_returnOkStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        CustomerPatchRequestDto requestDto = CustomerPatchRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PATCH", "/customers/" + customerId, requestBody, 200, HttpHeadersMother.random());
    }

    @Test
    void given_customerId_when_deleteCustomer_then_returnNoContentStatus() throws Exception {
        String customerId = UuidMother.random().toString();
        assertRequest("DELETE", "/customers/" + customerId, 204, HttpHeadersMother.random());
    }
}