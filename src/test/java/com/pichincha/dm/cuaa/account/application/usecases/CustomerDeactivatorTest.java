package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.DeleteCustomerOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.identifiers.CustomerId;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerIdMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class CustomerDeactivatorTest {

    @Mock
    private DeleteCustomerOutputPort customerPersistence;

    @InjectMocks
    private CustomerDeactivator customerDeactivator;

    @Test
    void given_existingCustomerId_when_deleteCustomer_then_deactivateInPersistence() {
        CustomerId customerId = CustomerIdMother.random();

        when(customerPersistence.deactivate(customerId)).thenReturn(Mono.empty());

        customerDeactivator.deleteCustomer(customerId).block();

        verify(customerPersistence).deactivate(customerId);
    }
}