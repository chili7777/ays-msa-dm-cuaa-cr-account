package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.AccountCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DuplicateConstraintIntegrationTest extends RequestTestCase {

    @Test
    void given_duplicateIdentification_when_patchCustomer_then_return400() throws Exception {
        String identification1 = "ID1" + System.currentTimeMillis();
        String identification2 = "ID2" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "duplicate-test");

        // Create Customer 1
        CustomerCreateRequestDto customer1 = createCustomerDto("User 1", identification1);
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customer1), 201, headers);
        
        // Create Customer 2
        CustomerCreateRequestDto customer2 = createCustomerDto("User 2", identification2);
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customer2), 201, headers);

        byte[] listResp = assertRequest("GET", "/customers?identification=" + identification2, 200, headers);
        List<CustomerDto> customers = JsonMother.toList(listResp, CustomerDto.class);
        UUID customer2Id = customers.get(0).getCustomerId();

        // Try to patch Customer 2 with identification of Customer 1
        CustomerPatchRequestDto patchReq = new CustomerPatchRequestDto();
        patchReq.setIdentification(identification1);
        
        assertRequestWithBody("PATCH", "/customers/" + customer2Id, JsonMother.fromObject(patchReq), 400, headers);
    }

    private CustomerCreateRequestDto createCustomerDto(String name, String id) {
        CustomerCreateRequestDto req = new CustomerCreateRequestDto();
        req.setName(name);
        req.setIdentification(id);
        req.setEmail(id + "@test.com");
        req.setPhone("0999999999");
        req.setAddress("Quito");
        req.setGender(CustomerCreateRequestDto.GenderEnum.MALE);
        req.setAge(30);
        req.setPassword("password123");
        req.setStatus(true);
        return req;
    }

    @Test
    void given_nonExistentCustomer_when_createAccount_then_return400() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "notfound-test");

        AccountCreateRequestDto accountReq = new AccountCreateRequestDto();
        accountReq.setClientId(UUID.randomUUID());
        accountReq.setAccountNumber("ACC-NOT-FOUND");
        accountReq.setAccountType(AccountCreateRequestDto.AccountTypeEnum.SAVINGS);
        accountReq.setInitialBalance(100.0);
        accountReq.setStatus(true);

        assertRequestWithBody("POST", "/accounts", JsonMother.fromObject(accountReq), 400, headers);
    }

    @Test
    void given_duplicateIdentification_when_createCustomer_then_return400() throws Exception {
        String identification = "DUP" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "duplicate-test");

        CustomerCreateRequestDto customerReq = new CustomerCreateRequestDto();
        customerReq.setName("User 1");
        customerReq.setIdentification(identification);
        customerReq.setEmail("u1@test.com");
        customerReq.setPhone("0999999991");
        customerReq.setAddress("Quito");
        customerReq.setGender(CustomerCreateRequestDto.GenderEnum.MALE);
        customerReq.setAge(30);
        customerReq.setPassword("password123");
        customerReq.setStatus(true);

        // First creation - OK
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customerReq), 201, headers);

        // Second creation with same identification - Should be 400 (Conflict handled by GlobalExceptionHandler)
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customerReq), 400, headers);
    }

    @Test
    void given_duplicateAccountNumber_when_createAccount_then_return400() throws Exception {
        String identification = "ACCDUP" + System.currentTimeMillis();
        String accountNumber = "ACC" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "duplicate-test");

        // 1. Create Customer
        CustomerCreateRequestDto customerReq = new CustomerCreateRequestDto();
        customerReq.setName("Account User");
        customerReq.setIdentification(identification);
        customerReq.setEmail("acc@test.com");
        customerReq.setPhone("0999999992");
        customerReq.setAddress("Quito");
        customerReq.setGender(CustomerCreateRequestDto.GenderEnum.MALE);
        customerReq.setAge(30);
        customerReq.setPassword("password123");
        customerReq.setStatus(true);
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customerReq), 201, headers);

        byte[] listResp = assertRequest("GET", "/customers?identification=" + identification, 200, headers);
        List<CustomerDto> customers = JsonMother.toList(listResp, CustomerDto.class);
        UUID customerId = customers.get(0).getCustomerId();

        // 2. Create Account
        AccountCreateRequestDto accountReq = new AccountCreateRequestDto();
        accountReq.setClientId(customerId);
        accountReq.setAccountNumber(accountNumber);
        accountReq.setAccountType(AccountCreateRequestDto.AccountTypeEnum.SAVINGS);
        accountReq.setInitialBalance(1000.0);
        accountReq.setStatus(true);

        // First creation - OK
        assertRequestWithBody("POST", "/accounts", JsonMother.fromObject(accountReq), 201, headers);

        // Second creation with same account number - Should be 400
        assertRequestWithBody("POST", "/accounts", JsonMother.fromObject(accountReq), 400, headers);
    }
}