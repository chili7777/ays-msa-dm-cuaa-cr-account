package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.CustomerEntity;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CustomerRepositoryMapperTest {

    private final CustomerRepositoryMapper mapper = Mappers.getMapper(CustomerRepositoryMapper.class);

    @Test
    void shouldMapDomainToEntityWithAllFields() {
        Customer domain = CustomerMother.random();

        CustomerEntity entity = mapper.toCustomerEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.id().getValue(), entity.id());
        assertEquals(domain.identification().getValue(), entity.identification());
        assertEquals(domain.fullName().getValue(), entity.fullName());
        assertEquals(domain.gender().getValue(), entity.gender());
        assertEquals(domain.age().getValue(), entity.age());
        assertEquals(domain.email().getValue(), entity.email());
        assertEquals(domain.phone().getValue(), entity.phone());
        assertEquals(domain.address().getValue(), entity.address());
        assertEquals(domain.password().getValue(), entity.password());
        assertEquals(domain.status().getValue(), entity.status());
    }

    @Test
    void shouldMapEntityToDomainWithAllFields() {
        Customer domainOriginal = CustomerMother.random();
        CustomerEntity entity = mapper.toCustomerEntity(domainOriginal);

        Customer domainMapped = mapper.toCustomer(entity);

        assertNotNull(domainMapped);
        assertEquals(entity.id(), domainMapped.id().getValue());
        assertEquals(entity.identification(), domainMapped.identification().getValue());
        assertEquals(entity.fullName(), domainMapped.fullName().getValue());
        assertEquals(entity.gender(), domainMapped.gender().getValue());
        assertEquals(entity.age(), domainMapped.age().getValue());
        assertEquals(entity.email(), domainMapped.email().getValue());
        assertEquals(entity.phone(), domainMapped.phone().getValue());
        assertEquals(entity.address(), domainMapped.address().getValue());
        assertEquals(entity.password(), domainMapped.password().getValue());
        assertEquals(entity.status(), domainMapped.status().getValue());
    }
}