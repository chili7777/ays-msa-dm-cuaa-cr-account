package com.pichincha.dm.cuaa.account.application.usecases;

import com.pichincha.dm.cuaa.account.application.usecases.ports.input.LoginInputPort;
import com.pichincha.dm.cuaa.account.application.usecases.ports.output.GetCustomerByIdentificationOutputPort;
import com.pichincha.dm.cuaa.account.domain.annotations.UseCaseService;
import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.UnauthorizedException;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCaseService
@RequiredArgsConstructor
public class CustomerAuthenticator implements LoginInputPort {
    private final GetCustomerByIdentificationOutputPort customerPersistence;

    @Override
    public Mono<Customer> login(Identification identification, Password password) {
        return customerPersistence.getByIdentification(identification)
                .filter(customer -> customer.password().equals(password))
                .switchIfEmpty(Mono.error(new UnauthorizedException("Credenciales inválidas")));
    }
}