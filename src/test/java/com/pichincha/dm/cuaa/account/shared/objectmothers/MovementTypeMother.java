package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementType;

public final class MovementTypeMother {
    public static MovementType random() {
        return new MovementType(FakerMother.random().options().option("DEPOSIT", "WITHDRAWAL"));
    }
}