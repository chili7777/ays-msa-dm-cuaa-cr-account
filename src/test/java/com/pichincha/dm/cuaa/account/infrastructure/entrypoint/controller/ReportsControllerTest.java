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

public class ReportsControllerTest extends RequestTestCase {

    @Test
    void given_validParams_when_getReportPdf_then_returnPdfContent() throws Exception {
        // Prepare data
        String identification = "REP" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-guid", UUID.randomUUID().toString());
        headers.set("x-app", "report-test");

        // 1. Create Customer
        CustomerCreateRequestDto customerReq = new CustomerCreateRequestDto();
        customerReq.setName("Report Test User");
        customerReq.setIdentification(identification);
        customerReq.setEmail("rep@test.com");
        customerReq.setPhone("0999999999");
        customerReq.setAddress("Quito");
        customerReq.setGender(CustomerCreateRequestDto.GenderEnum.MALE);
        customerReq.setAge(30);
        customerReq.setPassword("password123");
        customerReq.setStatus(true);
        assertRequestWithBody("POST", "/customers", JsonMother.fromObject(customerReq), 201, headers);

        byte[] listResp = assertRequest("GET", "/customers?identification=" + identification, 200, headers);
        UUID customerId = JsonMother.toList(listResp, CustomerDto.class).get(0).getCustomerId();

        // 2. Create Account
        AccountCreateRequestDto accountReq = new AccountCreateRequestDto();
        accountReq.setClientId(customerId);
        accountReq.setAccountNumber("AR" + (System.currentTimeMillis() % 1000000000L));
        accountReq.setAccountType(AccountCreateRequestDto.AccountTypeEnum.SAVINGS);
        accountReq.setInitialBalance(500.0);
        accountReq.setStatus(true);
        assertRequestWithBody("POST", "/accounts", JsonMother.fromObject(accountReq), 201, headers);

        // 3. Test Report with /account-statement
        String url = String.format("/api/v1/reports/account-statement?customerId=%s&startDate=2020-01-01&endDate=2030-12-31&format=pdf", customerId);
        byte[] pdfContent = assertRequest("GET", url, 200, headers);
        assertNotNull(pdfContent);
        assertTrue(pdfContent.length > 0);

        // 4. Test Report with /reportes and fecha param
        String url2 = String.format("/api/v1/reports/reportes?customerId=%s&fecha=2020-01-01,2030-12-31&format=pdf", customerId);
        byte[] pdfContent2 = assertRequest("GET", url2, 200, headers);
        assertNotNull(pdfContent2);
        assertTrue(pdfContent2.length > 0);
    }

    @Test
    void given_missingHeaders_when_getReport_then_return400() throws Exception {
        String url = "/api/v1/reports/account-statement?customerId=" + UUID.randomUUID() + "&format=pdf";
        assertRequest("GET", url, 400, new HttpHeaders());
    }
}