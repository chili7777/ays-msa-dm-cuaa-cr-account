package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Status implements ValueObject<Boolean> {

    @Getter
    private final Boolean value;

    public Status(Boolean value) {
        ensureValidStatus(value);
        this.value = value;
    }

    private void ensureValidStatus(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }

    public boolean isActive() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}