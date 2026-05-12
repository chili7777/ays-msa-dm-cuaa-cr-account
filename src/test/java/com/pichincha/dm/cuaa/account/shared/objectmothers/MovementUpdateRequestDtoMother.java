package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementUpdateRequestDto;

public final class MovementUpdateRequestDtoMother {

    private MovementUpdateRequestDtoMother() {
    }

    public static MovementUpdateRequestDto random() {
        MovementUpdateRequestDto dto = new MovementUpdateRequestDto();
        dto.setMovementType(FakerMother.faker().options().option(MovementUpdateRequestDto.MovementTypeEnum.values()));
        dto.setAmount(FakerMother.faker().number().randomDouble(2, 1, 1000));
        dto.setMovementDate(java.time.OffsetDateTime.now());
        dto.setStatus(true);
        return dto;
    }
}