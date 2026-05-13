package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateAccountOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateMovementOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Account;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.Movement;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.AccountId;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovementFilterIntegrationTest extends RequestTestCase {

    @Autowired
    private CreateCustomerOutputPort createCustomerOutputPort;

    @Autowired
    private CreateAccountOutputPort createAccountOutputPort;

    @Autowired
    private CreateMovementOutputPort createMovementOutputPort;

    @Test
    void given_movementsInDifferentDates_when_filterByDate_then_returnOnlyMatchMovements() throws Exception {
        // Setup data
        CustomerId customerId = CustomerIdMother.random();
        Customer customer = CustomerMother.withId(customerId);
        createCustomerOutputPort.save(customer).block();

        AccountId accountId = AccountIdMother.random();
        Account account = AccountMother.withIdAndCustomerId(accountId, customerId);
        createAccountOutputPort.save(account).block();

        // Movement 1: 2026-05-01
        Movement m1 = MovementMother.withAccountIdAndDate(accountId, LocalDateTime.of(2026, 5, 1, 10, 0));
        createMovementOutputPort.save(m1).block();

        Movement m2 = MovementMother.withAccountIdAndDate(accountId, LocalDateTime.of(2026, 5, 5, 10, 0));
        createMovementOutputPort.save(m2).block();

        Movement m3 = MovementMother.withAccountIdAndDate(accountId, LocalDateTime.of(2026, 5, 10, 10, 0));
        createMovementOutputPort.save(m3).block();

        String url = "/movements?accountId=" + accountId.getValue() + "&fromDate=2026-05-04&toDate=2026-05-06";

        byte[] response = assertRequest("GET", url, 200, HttpHeadersMother.random());
        List<MovementDto> result = JsonMother.toList(response, MovementDto.class);

        assertEquals(1, result.size());
        assertEquals(m2.movementId().getValue(), result.get(0).getMovementId().toString());

        url = "/movements?accountId=" + accountId.getValue();
        response = assertRequest("GET", url, 200, HttpHeadersMother.random());
        result = JsonMother.toList(response, MovementDto.class);
        assertEquals(3, result.size());
    }
}