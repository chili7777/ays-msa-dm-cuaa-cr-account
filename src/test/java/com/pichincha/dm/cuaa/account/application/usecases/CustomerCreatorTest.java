package com.pichincha.dm.cuaa.account.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.CreateCustomerOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
final class CustomerCreatorTest {

    @Mock
    private CreateCustomerOutputPort customerPersistence;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private CustomerCreator customerCreator;

    @Test
    void given_validCustomerData_when_createCustomer_then_hashPasswordAndPersistCustomer() {
        Customer customer = CustomerMother.random();
        String rawPassword = customer.password().getValue();
        String hashed = "hashedPassword";

        when(passwordHasher.hash(rawPassword)).thenReturn(hashed);
        when(customerPersistence.save(any(Customer.class))).thenReturn(Mono.empty());

        customerCreator.createCustomer(customer).block();

        verify(passwordHasher).hash(rawPassword);
        verify(customerPersistence).save(argThat(cust ->
            cust.password().getValue().equals(hashed)
        ));
    }

    @Test
    void given_customerWithoutId_when_createCustomer_then_generateIdAndPersist() {
        Customer customerWithoutId = new Customer(
                null,
                CustomerMother.random().identification(),
                CustomerMother.random().fullName(),
                CustomerMother.random().gender(),
                CustomerMother.random().age(),
                CustomerMother.random().email(),
                CustomerMother.random().phone(),
                CustomerMother.random().address(),
                new Password("plainPassword"),
                CustomerMother.random().status(),
                CustomerMother.random().role()
        );

        when(passwordHasher.hash(any())).thenReturn("hashed");
        when(customerPersistence.save(any(Customer.class))).thenReturn(Mono.empty());

        customerCreator.createCustomer(customerWithoutId).block();

        verify(customerPersistence).save(argThat(cust -> cust.id() != null));
    }
}