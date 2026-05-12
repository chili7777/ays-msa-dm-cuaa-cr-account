package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Password;
import reactor.core.publisher.Mono;

public interface LoginInputPort {
    Mono<Customer> login(Identification identification, Password password);
}