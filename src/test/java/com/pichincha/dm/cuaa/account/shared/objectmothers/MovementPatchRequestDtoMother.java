package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementPatchRequestDto;

public final class MovementPatchRequestDtoMother {

    private MovementPatchRequestDtoMother() {
    }

    public static MovementPatchRequestDto random() {
        MovementPatchRequestDto dto = new MovementPatchRequestDto();
        dto.setAmount(FakerMother.faker().number().randomDouble(2, 1, 1000));
        return dto;
    }
}