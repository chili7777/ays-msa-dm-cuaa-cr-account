package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class MovementId extends Identifier {
    public MovementId(String value) {
        super(value);
        ensureValidUuid(value);
    }

    private void ensureValidUuid(String value) {
        UUID.fromString(value);
    }
}