package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerPatchRequestDto;

public final class CustomerPatchRequestDtoMother {

    private CustomerPatchRequestDtoMother() {
    }

    public static CustomerPatchRequestDto random() {
        CustomerPatchRequestDto dto = new CustomerPatchRequestDto();
        dto.setName(FakerMother.faker().name().fullName());
        dto.setPhone(FakerMother.faker().number().digits(10));
        dto.setAddress(FakerMother.faker().address().fullAddress());
        dto.setStatus(true);
        return dto;
    }
}