package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.CustomerCreateRequestDto;

public final class CustomerCreateRequestDtoMother {

    private CustomerCreateRequestDtoMother() {
    }

    public static CustomerCreateRequestDto random() {
        return create(
                FakerMother.faker().name().fullName(),
                FakerMother.faker().number().digits(10),
                FakerMother.faker().internet().emailAddress(),
                FakerMother.faker().number().digits(10),
                FakerMother.faker().address().fullAddress(),
                true,
                FakerMother.faker().internet().password(),
                CustomerCreateRequestDto.GenderEnum.OTHER,
                30
        );
    }

    public static CustomerCreateRequestDto create(String name,
                                                  String identification,
                                                  String email,
                                                  String phone,
                                                  String address,
                                                  Boolean status,
                                                  String password,
                                                  CustomerCreateRequestDto.GenderEnum gender,
                                                  Integer age) {
        CustomerCreateRequestDto dto = new CustomerCreateRequestDto();
        dto.setName(name);
        dto.setIdentification(identification);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setAddress(address);
        dto.setStatus(status);
        dto.setPassword(password);
        dto.setGender(gender);
        dto.setAge(age);
        return dto;
    }
}