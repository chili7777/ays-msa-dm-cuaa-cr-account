package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class InitialBalance implements ValueObject<Double> {

    @Getter
    private final Double value;

    public InitialBalance(Double value) {
        ensureValidBalance(value);
        this.value = value;
    }

    private void ensureValidBalance(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("Initial balance cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}