package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class MovementType implements ValueObject<String> {
    private static final Set<String> VALID_TYPES = Set.of("DEPOSIT", "WITHDRAWAL");
    @Getter
    private final String value;

    public MovementType(String value) {
        ensureValidType(value);
        this.value = value;
    }

    private void ensureValidType(String value) {
        if (value == null || !VALID_TYPES.contains(value)) {
            throw new IllegalArgumentException("Invalid movement type: " + value);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}