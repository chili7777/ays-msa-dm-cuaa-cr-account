package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountUpdateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.InMemoryAccountRepository;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.*;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountsControllerTest extends RequestTestCase {

    @Autowired
    private CreateCustomerOutputPort createCustomerOutputPort;

    @Autowired
    private CreateAccountOutputPort createAccountOutputPort;

    @Autowired
    private InMemoryAccountRepository repository;

    @BeforeEach
    void setUp() {
        repository.clear();
    }

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
        CustomerId customerId = CustomerIdMother.random();
        createCustomerOutputPort.save(CustomerMother.withId(customerId)).block();
        createAccountOutputPort.save(AccountMother.withId(new AccountId(accountId), customerId)).block();

        assertRequest("GET", "/accounts/" + accountId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_nonExistentAccountId_when_getAccountById_then_returnNotFound() throws Exception {
        String accountId = UuidMother.random().toString();
        assertRequest("GET", "/accounts/" + accountId, 404, HttpHeadersMother.random());
    }

    @Test
    void given_validAccountUpdateRequest_when_replaceAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        CustomerId customerId = CustomerIdMother.random();
        String accountNumber = "1234567890123";
        Double initialBalance = 1000.0;

        createCustomerOutputPort.save(CustomerMother.withId(customerId)).block();
        createAccountOutputPort.save(AccountMother.create(new AccountId(accountId), customerId, accountNumber, "SAVINGS", initialBalance, true)).block();

        AccountUpdateRequestDto requestDto = AccountUpdateRequestDtoMother.create("CURRENT", false);
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PUT", "/accounts/" + accountId, requestBody, 204, HttpHeadersMother.random());

        // Verify that accountNumber and initialBalance are preserved
        repository.findById(new AccountId(accountId))
                .doOnNext(account -> {
                    org.junit.jupiter.api.Assertions.assertEquals(accountNumber, account.accountNumber().getValue());
                    org.junit.jupiter.api.Assertions.assertEquals(initialBalance, account.initialBalance().getValue());
                    org.junit.jupiter.api.Assertions.assertEquals("CURRENT", account.accountType().getValue());
                    org.junit.jupiter.api.Assertions.assertEquals(false, account.status().getValue());
                }).block();
    }

    @Test
    void given_validAccountPatchRequest_when_patchAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        CustomerId customerId = CustomerIdMother.random();
        createCustomerOutputPort.save(CustomerMother.withId(customerId)).block();
        createAccountOutputPort.save(AccountMother.withId(new AccountId(accountId), customerId)).block();

        AccountPatchRequestDto requestDto = AccountPatchRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PATCH", "/accounts/" + accountId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_accountId_when_deleteAccount_then_returnNoContentStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        CustomerId customerId = CustomerIdMother.random();
        createCustomerOutputPort.save(CustomerMother.withId(customerId)).block();
        createAccountOutputPort.save(AccountMother.withId(new AccountId(accountId), customerId)).block();

        assertRequest("DELETE", "/accounts/" + accountId, 204, HttpHeadersMother.random());
    }

    @Test
    void given_nonExistentAccountId_when_deleteAccount_then_returnNotFound() throws Exception {
        String accountId = UuidMother.random().toString();
        assertRequest("DELETE", "/accounts/" + accountId, 404, HttpHeadersMother.random());
    }
}