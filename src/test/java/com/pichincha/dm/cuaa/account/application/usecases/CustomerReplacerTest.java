package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdentificationOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
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

    @Mock
    private GetCustomerByIdentificationOutputPort getCustomerByIdentification;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private CustomerReplacer customerReplacer;

    @Test
    void given_validCustomerData_when_replaceCustomer_then_hashPasswordAndUpdateInPersistence() {
        CustomerId customerId = CustomerIdMother.random();
        Customer customer = CustomerMother.random();
        String rawPassword = customer.password().getValue();
        String hashed = "hashedPassword";

        when(getCustomerByIdentification.getByIdentification(any())).thenReturn(Mono.empty());
        when(passwordHasher.hash(rawPassword)).thenReturn(hashed);
        when(customerPersistence.update(any(), any())).thenReturn(Mono.empty());

        customerReplacer.replaceCustomer(customerId, customer).block();

        verify(passwordHasher).hash(rawPassword);
        verify(customerPersistence).update(argThat(id -> id.equals(customerId)), argThat(cust -> 
            cust.password().getValue().equals(hashed)
        ));
    }
}