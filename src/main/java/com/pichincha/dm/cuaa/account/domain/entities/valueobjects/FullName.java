package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's full name.
 * Encapsulates name validation logic and prevents invalid or empty names in the domain.
 */
@Value
public class FullName implements ValueObject<String> {

    String value;

    public FullName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Full name must not be null or empty");
        }
        this.value = value;
    }

}