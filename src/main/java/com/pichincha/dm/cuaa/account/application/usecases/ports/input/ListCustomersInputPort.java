package com.pichincha.dm.cuaa.account.application.usecases.ports.input;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import reactor.core.publisher.Flux;

public interface ListCustomersInputPort {
    Flux<Customer> listCustomers();
}