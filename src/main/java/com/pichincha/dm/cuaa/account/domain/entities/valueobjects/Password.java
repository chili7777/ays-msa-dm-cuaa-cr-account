package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's password.
 */
@Value
public class Password implements ValueObject<String> {
    String value;

    public Password(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
        this.value = value;
    }
}