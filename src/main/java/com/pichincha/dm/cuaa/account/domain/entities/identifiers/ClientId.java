package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ClientId extends Identifier {

    public ClientId(String value) {
        super(value);
        ensureValidUuid(value);
    }

    private void ensureValidUuid(String value) {
        UUID.fromString(value);
    }
}