package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class MovementDate implements ValueObject<LocalDateTime> {
    @Getter
    private final LocalDateTime value;

    public MovementDate(LocalDateTime value) {
        ensureNotNull(value);
        this.value = value;
    }

    private void ensureNotNull(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("Movement date cannot be null");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}