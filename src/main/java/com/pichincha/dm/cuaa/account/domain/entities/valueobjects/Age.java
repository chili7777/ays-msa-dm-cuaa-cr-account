package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a person's age.
 */
@Value
public class Age implements ValueObject<Integer> {
    Integer value;

    public Age(Integer value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Age must not be null and must be non-negative");
        }
        this.value = value;
    }
}