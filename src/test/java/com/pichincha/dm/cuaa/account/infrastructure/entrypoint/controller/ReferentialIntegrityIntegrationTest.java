package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.AccountMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.HttpHeadersMother;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.CustomerJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.jpa.AccountJpaRepository;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.CustomerRepositoryMapper;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper.AccountRepositoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ReferentialIntegrityIntegrationTest extends RequestTestCase {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @Autowired
    private CustomerRepositoryMapper customerMapper;

    @Autowired
    private AccountRepositoryMapper accountMapper;

    @Test
    void given_customerWithAccounts_when_deleteCustomer_then_shouldHandleErrorGracefully() throws Exception {
        var customer = CustomerMother.random();
        customerJpaRepository.saveAndFlush(customerMapper.toCustomerEntity(customer));

        var account = AccountMother.withIdAndCustomerId(AccountIdMother.random(), customer.id());
        accountJpaRepository.saveAndFlush(accountMapper.toAccountEntity(account));


        String url = "/customers/" + customer.id().getValue();
        assertRequest("DELETE", url, 204, HttpHeadersMother.random());

        assertEquals(0, customerJpaRepository.count());
        assertEquals(0, accountJpaRepository.count());
    }
}