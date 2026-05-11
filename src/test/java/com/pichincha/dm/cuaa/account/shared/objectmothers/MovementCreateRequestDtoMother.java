package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementCreateRequestDto;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class MovementCreateRequestDtoMother {

    private MovementCreateRequestDtoMother() {
    }

    public static MovementCreateRequestDto random() {
        MovementCreateRequestDto dto = new MovementCreateRequestDto();
        dto.setAccountId(UuidMother.random());
        dto.setMovementType(FakerMother.faker().options().option(MovementCreateRequestDto.MovementTypeEnum.values()));
        dto.setAmount(FakerMother.faker().number().randomDouble(2, 1, 1000));
        dto.setMovementDate(OffsetDateTime.now());
        return dto;
    }

    public static MovementCreateRequestDto create(UUID accountId,
                                                   MovementCreateRequestDto.MovementTypeEnum type,
                                                   Double amount) {
        MovementCreateRequestDto dto = new MovementCreateRequestDto();
        dto.setAccountId(accountId);
        dto.setMovementType(type);
        dto.setAmount(amount);
        return dto;
    }
}