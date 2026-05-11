package com.pichincha.dm.cuaa.account.shared.objectmothers;

import com.pichincha.dm.cuaa.account.domain.entities.valueobjects.MovementDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public final class MovementDateMother {
    public static MovementDate random() {
        return new MovementDate(LocalDateTime.ofInstant(
                FakerMother.random().date().past(30, TimeUnit.DAYS).toInstant(),
                ZoneId.systemDefault()
        ));
    }
}