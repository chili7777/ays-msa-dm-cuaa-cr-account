package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.identifiers.MovementId;

public final class MovementIdMother {
    public static MovementId random() {
        return new MovementId(UuidMother.random());
    }
}