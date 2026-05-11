package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountCreateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountPatchRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountUpdateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.HttpHeadersMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountsControllerTest extends RequestTestCase {

    @Autowired
    private CreateCustomerOutputPort createCustomerOutputPort;

    @Test
    void given_validAccountCreateRequest_when_createAccount_then_returnCreatedStatus() throws Exception {
        AccountCreateRequestDto requestDto = AccountCreateRequestDtoMother.random();
        
        // Save customer to satisfy referential integrity
        createCustomerOutputPort.save(CustomerMother.withId(new CustomerId(requestDto.getClientId().toString()))).block();

        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/accounts", requestBody, 201, HttpHeadersMother.random());
    }

    @Test
    void given_clientId_when_listAccounts_then_returnOkStatus() throws Exception {
        String clientId = UuidMother.random().toString();
        assertRequest("GET", "/accounts?clientId=" + clientId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_accountId_when_getAccountById_then_returnOkStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        assertRequest("GET", "/accounts/" + accountId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_validAccountUpdateRequest_when_replaceAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        AccountUpdateRequestDto requestDto = AccountUpdateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PUT", "/accounts/" + accountId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_validAccountPatchRequest_when_patchAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        AccountPatchRequestDto requestDto = AccountPatchRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PATCH", "/accounts/" + accountId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_accountId_when_deleteAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        assertRequest("DELETE", "/accounts/" + accountId, 204, HttpHeadersMother.random());
    }
}