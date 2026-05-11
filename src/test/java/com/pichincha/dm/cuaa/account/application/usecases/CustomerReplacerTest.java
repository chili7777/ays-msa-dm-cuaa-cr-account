package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ReplaceCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerIdMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class CustomerReplacerTest {

    @Mock
    private ReplaceCustomerOutputPort customerPersistence;

    @InjectMocks
    private CustomerReplacer customerReplacer;

    @Test
    void given_validCustomerData_when_replaceCustomer_then_updateInPersistence() {
        CustomerId customerId = CustomerIdMother.random();
        Customer customer = CustomerMother.random();

        when(customerPersistence.update(customerId, customer)).thenReturn(Mono.empty());

        customerReplacer.replaceCustomer(customerId, customer).block();

        verify(customerPersistence).update(customerId, customer);
    }
}