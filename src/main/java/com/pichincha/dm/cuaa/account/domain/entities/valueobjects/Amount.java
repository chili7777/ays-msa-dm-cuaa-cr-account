package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Amount implements ValueObject<Double> {
    @Getter
    private final Double value;

    public Amount(Double value) {
        ensureValidAmount(value);
        this.value = value;
    }

    private void ensureValidAmount(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (value == 0) {
            throw new IllegalArgumentException("Amount cannot be zero");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}