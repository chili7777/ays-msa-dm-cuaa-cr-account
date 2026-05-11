package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's phone number.
 * Ensures the value is provided and maintains a consistent format for communication.
 */
@Value
public class Phone implements ValueObject<String> {

    String value;

    public Phone(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number must not be null or empty");
        }
        this.value = value;
    }

}