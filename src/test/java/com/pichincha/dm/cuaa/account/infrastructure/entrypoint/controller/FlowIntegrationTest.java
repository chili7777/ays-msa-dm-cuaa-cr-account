package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.*;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FlowIntegrationTest extends RequestTestCase {

    @Test
    void fullFlowTest() throws Exception {
        String identification = "FLOW" + System.currentTimeMillis();
        String accountNumber = "ACC" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "flow-test");

        // --- CUSTOMER FLOW ---
        // 1. Create Customer
        CustomerCreateRequestDto customerReq = new CustomerCreateRequestDto();
        customerReq.setName("Flow Test User");
        customerReq.setIdentification(identification);
        customerReq.setEmail("flow@test.com");
        customerReq.setPhone("0999999999");
        customerReq.setAddress("Quito");
        customerReq.setGender(CustomerCreateRequestDto.GenderEnum.MALE);
        customerReq.setAge(30);
        customerReq.setPassword("password123");
        customerReq.setStatus(true);

        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customerReq), 201, headers);

        // 2. List and Find
        byte[] listResp = assertRequest("GET", "/customers?identification=" + identification, 200, headers);
        List<CustomerDto> customers = JsonMother.toList(listResp, CustomerDto.class);
        assertFalse(customers.isEmpty());
        UUID customerId = customers.get(0).getCustomerId();

        // 3. Get by ID
        assertRequest("GET", "/customers/" + customerId, 200, headers);

        // 4. Update
        CustomerUpdateRequestDto updateReq = new CustomerUpdateRequestDto();
        updateReq.setName("Updated Flow User");
        updateReq.setIdentification(identification);
        updateReq.setEmail("upd@test.com");
        updateReq.setPhone("022222222");
        updateReq.setAddress("Guayaquil");
        updateReq.setGender(CustomerUpdateRequestDto.GenderEnum.MALE);
        updateReq.setAge(31);
        updateReq.setPassword("password123");
        updateReq.setStatus(true);
        assertRequestWithBody("PUT", "/customers/" + customerId, JsonMother.fromObject(updateReq), 204, headers);

        // 5. Patch
        CustomerPatchRequestDto patchReq = new CustomerPatchRequestDto();
        patchReq.setStatus(true); // Keep it active
        assertRequestWithBody("PATCH", "/customers/" + customerId, JsonMother.fromObject(patchReq), 204, headers);

        // --- ACCOUNT FLOW ---
        // 6. Create Account
        AccountCreateRequestDto accountReq = new AccountCreateRequestDto();
        accountReq.setClientId(customerId);
        accountReq.setAccountNumber(accountNumber);
        accountReq.setAccountType(AccountCreateRequestDto.AccountTypeEnum.SAVINGS);
        accountReq.setInitialBalance(1000.0);
        accountReq.setStatus(true);

        assertRequestWithBody("POST", "/accounts", JsonMother.fromObject(accountReq), 201, headers);

        // 7. List and Find
        byte[] accListResp = assertRequest("GET", "/accounts?accountNumber=" + accountNumber, 200, headers);
        List<AccountDto> accounts = JsonMother.toList(accListResp, AccountDto.class);
        assertFalse(accounts.isEmpty());
        UUID accountId = accounts.get(0).getAccountId();

        // 8. Update Account
        AccountUpdateRequestDto accUpdateReq = new AccountUpdateRequestDto();
        accUpdateReq.setAccountType(AccountUpdateRequestDto.AccountTypeEnum.CURRENT);
        accUpdateReq.setStatus(true);
        assertRequestWithBody("PUT", "/accounts/" + accountId, JsonMother.fromObject(accUpdateReq), 204, headers);

        // --- MOVEMENT FLOW ---
        // 9. Create Movement
        MovementCreateRequestDto moveReq = new MovementCreateRequestDto();
        moveReq.setAccountId(accountId);
        moveReq.setMovementType(MovementCreateRequestDto.MovementTypeEnum.DEPOSIT);
        moveReq.setAmount(100.0);
        moveReq.setMovementDate(OffsetDateTime.now());
        moveReq.setDescription("Flow deposit");

        assertRequestWithBody("POST", "/movements", JsonMother.fromObject(moveReq), 201, headers);

        // 10. List Movements
        byte[] moveListResp = assertRequest("GET", "/movements?accountId=" + accountId, 200, headers);
        List<MovementDto> movements = JsonMother.toList(moveListResp, MovementDto.class);
        assertFalse(movements.isEmpty());
        UUID movementId = movements.get(0).getMovementId();

        // 11. Patch Movement
        MovementPatchRequestDto movePatch = new MovementPatchRequestDto();
        movePatch.setAmount(150.0);
        assertRequestWithBody("PATCH", "/movements/" + movementId, JsonMother.fromObject(movePatch), 204, headers);

        // --- DELETE FLOW ---
        // 12. Delete Movement
        assertRequest("DELETE", "/movements/" + movementId, 204, headers);

        // 13. Delete Account
        assertRequest("DELETE", "/accounts/" + accountId, 204, headers);

        // 14. Delete Customer
        assertRequest("DELETE", "/customers/" + customerId, 204, headers);
    }
}