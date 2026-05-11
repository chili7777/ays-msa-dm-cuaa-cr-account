package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdOutputPort;
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
final class CustomerFinderTest {

    @Mock
    private GetCustomerByIdOutputPort customerPersistence;

    @InjectMocks
    private CustomerFinder customerFinder;

    @Test
    void given_existingCustomerId_when_getCustomerById_then_returnCustomer() {
        CustomerId customerId = CustomerIdMother.random();
        Customer expectedCustomer = CustomerMother.withId(customerId);

        when(customerPersistence.findById(customerId)).thenReturn(Mono.just(expectedCustomer));

        Customer actualCustomer = customerFinder.getCustomerById(customerId).block();

        assertEquals(expectedCustomer, actualCustomer);
        verify(customerPersistence).findById(customerId);
    }
}