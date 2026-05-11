package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PatchCustomerOutputPort;
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
final class CustomerPatcherTest {

    @Mock
    private PatchCustomerOutputPort customerPersistence;

    @InjectMocks
    private CustomerPatcher customerPatcher;

    @Test
    void given_validCustomerData_when_patchCustomer_then_patchInPersistence() {
        CustomerId customerId = CustomerIdMother.random();
        Customer customer = CustomerMother.random();

        when(customerPersistence.patch(customerId, customer)).thenReturn(Mono.empty());

        customerPatcher.patchCustomer(customerId, customer).block();

        verify(customerPersistence).patch(customerId, customer);
    }
}