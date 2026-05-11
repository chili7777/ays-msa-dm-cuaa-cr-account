package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;

public final class CustomerPatchRequestDtoMother {

    private CustomerPatchRequestDtoMother() {
    }

    public static CustomerPatchRequestDto random() {
        CustomerPatchRequestDto dto = new CustomerPatchRequestDto();
        dto.setName(FakerMother.faker().name().fullName());
        dto.setEmail(FakerMother.faker().internet().emailAddress());
        dto.setPhone(FakerMother.faker().phoneNumber().phoneNumber());
        dto.setAddress(FakerMother.faker().address().fullAddress());
        dto.setStatus(true);
        return dto;
    }
}