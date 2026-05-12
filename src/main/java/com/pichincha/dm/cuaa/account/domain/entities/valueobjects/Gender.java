package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a person's gender.
 */
@Value
public class Gender implements ValueObject<String> {
    String value;

    public Gender(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Gender must not be null or empty");
        }
        this.value = value;
    }
}