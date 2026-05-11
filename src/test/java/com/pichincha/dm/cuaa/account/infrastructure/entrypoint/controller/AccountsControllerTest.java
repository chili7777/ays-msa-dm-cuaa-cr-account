package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountCreateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.HttpHeadersMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import org.junit.jupiter.api.Test;

class AccountsControllerTest extends RequestTestCase {

    @Test
    void given_validAccountCreateRequest_when_createAccount_then_returnCreatedStatus() throws Exception {
        AccountCreateRequestDto requestDto = AccountCreateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/accounts", requestBody, 201, HttpHeadersMother.random());
    }
}