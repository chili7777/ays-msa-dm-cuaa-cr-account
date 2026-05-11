package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's identification number.
 * Ensures the value is not null or empty and adheres to business format rules.
 */
@Value
public class Identification implements ValueObject<String> {

    String value;

    public Identification(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Identification must not be null or empty");
        }
        this.value = value;
    }

}