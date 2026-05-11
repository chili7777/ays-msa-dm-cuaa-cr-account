package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Value object representing a unique customer identifier.
 * Extends the base Identifier class to provide UUID validation and type-safe identity
 * specifically for Customer entities.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class CustomerId extends Identifier {

    public CustomerId(String value) {
        super(value);
        ensureValidUuid(value);
    }

    private void ensureValidUuid(String value) {
        java.util.UUID.fromString(value);
    }
}