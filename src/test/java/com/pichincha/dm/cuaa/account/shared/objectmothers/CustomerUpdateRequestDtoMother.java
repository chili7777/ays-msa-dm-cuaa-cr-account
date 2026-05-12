package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerUpdateRequestDto;

public final class CustomerUpdateRequestDtoMother {

    private CustomerUpdateRequestDtoMother() {
    }

    public static CustomerUpdateRequestDto random() {
        CustomerUpdateRequestDto dto = new CustomerUpdateRequestDto();
        dto.setName(FakerMother.faker().name().fullName());
        dto.setIdentification(FakerMother.faker().number().digits(10));
        dto.setEmail(FakerMother.faker().internet().emailAddress());
        dto.setPhone(FakerMother.faker().number().digits(10));
        dto.setAddress(FakerMother.faker().address().fullAddress());
        dto.setStatus(true);
        dto.setPassword(FakerMother.faker().internet().password());
        dto.setGender(CustomerUpdateRequestDto.GenderEnum.OTHER);
        dto.setAge(30);
        return dto;
    }
}