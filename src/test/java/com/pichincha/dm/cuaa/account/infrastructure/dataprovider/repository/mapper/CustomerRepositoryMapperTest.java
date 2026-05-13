package com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.infrastructure.dataprovider.repository.entities.ClientEntity;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CustomerRepositoryMapperTest {

    private final CustomerRepositoryMapper mapper = Mappers.getMapper(CustomerRepositoryMapper.class);

    @Test
    void shouldMapDomainToEntityWithAllFields() {
        Customer domain = CustomerMother.random();

        ClientEntity entity = mapper.toCustomerEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.id().getValue(), entity.getId());
        assertEquals(domain.identification().getValue(), entity.getIdentification());
        assertEquals(domain.fullName().getValue(), entity.getFullName());
        assertEquals(domain.gender().getValue(), entity.getGender());
        assertEquals(domain.age().getValue(), entity.getAge());
        assertEquals(domain.email().getValue(), entity.getEmail());
        assertEquals(domain.phone().getValue(), entity.getPhone());
        assertEquals(domain.address().getValue(), entity.getAddress());
        assertEquals(domain.password().getValue(), entity.getPassword());
        assertEquals(domain.status().getValue(), entity.getStatus());
    }

    @Test
    void shouldMapEntityToDomainWithAllFields() {
        Customer domainOriginal = CustomerMother.random();
        ClientEntity entity = mapper.toCustomerEntity(domainOriginal);

        Customer domainMapped = mapper.toCustomer(entity);

        assertNotNull(domainMapped);
        assertEquals(entity.getId(), domainMapped.id().getValue());
        assertEquals(entity.getIdentification(), domainMapped.identification().getValue());
        assertEquals(entity.getFullName(), domainMapped.fullName().getValue());
        assertEquals(entity.getGender(), domainMapped.gender().getValue());
        assertEquals(entity.getAge(), domainMapped.age().getValue());
        assertEquals(entity.getEmail(), domainMapped.email().getValue());
        assertEquals(entity.getPhone(), domainMapped.phone().getValue());
        assertEquals(entity.getAddress(), domainMapped.address().getValue());
        assertEquals(entity.getPassword(), domainMapped.password().getValue());
        assertEquals(entity.getStatus(), domainMapped.status().getValue());
    }
}