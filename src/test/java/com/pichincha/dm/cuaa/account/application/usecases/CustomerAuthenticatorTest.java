package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdentificationOutputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.UnauthorizedException;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAuthenticatorTest {

    @Mock
    private GetCustomerByIdentificationOutputPort customerPersistence;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private CustomerAuthenticator authenticator;

    private Identification identification;
    private Password password;
    private Customer customer;

    @BeforeEach
    void setUp() {
        identification = new Identification("1234567890");
        password = new Password("secret123");
        customer = mock(Customer.class);
    }

    @Test
    void shouldLoginSuccessfully() {
        when(customer.password()).thenReturn(new Password("hashedPassword"));
        when(customerPersistence.getByIdentification(identification)).thenReturn(Mono.just(customer));
        when(passwordHasher.matches(password.getValue(), "hashedPassword")).thenReturn(true);

        StepVerifier.create(authenticator.login(identification, password))
                .expectNext(customer)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenCustomerNotFound() {
        when(customerPersistence.getByIdentification(identification)).thenReturn(Mono.empty());

        StepVerifier.create(authenticator.login(identification, password))
                .expectError(UnauthorizedException.class)
                .verify();
    }

    @Test
    void shouldFailWhenPasswordInvalid() {
        when(customer.password()).thenReturn(new Password("hashedPassword"));
        when(customerPersistence.getByIdentification(identification)).thenReturn(Mono.just(customer));
        Password wrongPassword = new Password("wrong");
        when(passwordHasher.matches("wrong", "hashedPassword")).thenReturn(false);

        StepVerifier.create(authenticator.login(identification, wrongPassword))
                .expectError(UnauthorizedException.class)
                .verify();
    }
}