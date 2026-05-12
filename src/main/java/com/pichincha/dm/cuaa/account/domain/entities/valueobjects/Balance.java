package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Balance implements ValueObject<Double> {

    @Getter
    private final Double value;

    public Balance(Double value) {
        ensureValidBalance(value);
        this.value = value;
    }

    private void ensureValidBalance(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}