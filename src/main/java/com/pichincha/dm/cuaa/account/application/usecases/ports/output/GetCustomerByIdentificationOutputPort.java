package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.Identification;
import reactor.core.publisher.Mono;

public interface GetCustomerByIdentificationOutputPort {
    Mono<Customer> getByIdentification(Identification identification);
}