package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import reactor.core.publisher.Flux;

public interface ListCustomersOutputPort {
    Flux<Customer> findAll();
}