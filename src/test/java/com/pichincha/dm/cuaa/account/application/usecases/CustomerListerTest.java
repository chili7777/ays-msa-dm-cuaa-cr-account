package com.pichincha.dm.cuaa.account.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.ListCustomersOutputPort;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
final class CustomerListerTest {

    @Mock
    private ListCustomersOutputPort customerPersistence;

    @InjectMocks
    private CustomerLister customerLister;

    @Test
    void given_existingCustomers_when_listCustomers_then_returnAllCustomers() {
        Customer customer1 = CustomerMother.random();
        Customer customer2 = CustomerMother.random();
        List<Customer> expectedCustomers = List.of(customer1, customer2);

        when(customerPersistence.findAllCustomers()).thenReturn(Flux.fromIterable(expectedCustomers));

        List<Customer> actualCustomers = customerLister.listCustomers().collectList().block();

        assertEquals(expectedCustomers, actualCustomers);
        verify(customerPersistence).findAllCustomers();
    }
}