package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pichincha.dm.cuaa.account.domain.entities.Customer;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;
import com.pichincha.dm.cuaa.account.shared.objectmothers.CustomerCreateRequestDtoMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CustomerHttpRequestMapperTest {

    private final CustomerHttpRequestMapper mapper = Mappers.getMapper(CustomerHttpRequestMapper.class);

    @Test
    void shouldMapCreateDtoToDomainWithAllFields() {
        CustomerCreateRequestDto dto = CustomerCreateRequestDtoMother.random();
        
        Customer domain = mapper.toCustomer(dto);

        assertNotNull(domain);
        assertEquals(dto.getName(), domain.fullName().getValue());
        assertEquals(dto.getIdentification(), domain.identification().getValue());
        assertEquals(dto.getGender().name(), domain.gender().getValue());
        assertEquals(dto.getAge(), domain.age().getValue());
        assertEquals(dto.getEmail(), domain.email().getValue());
        assertEquals(dto.getPhone(), domain.phone().getValue());
        assertEquals(dto.getAddress(), domain.address().getValue());
        assertEquals(dto.getPassword(), domain.password().getValue());
        assertEquals(dto.getStatus(), domain.status().getValue());
    }
}