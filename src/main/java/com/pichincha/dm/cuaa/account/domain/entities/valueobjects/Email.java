package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's email address.
 * Provides basic structural validation for email formats within the domain layer.
 */
@Value
public class Email implements ValueObject<String> {

    String value;

    public Email(String value) {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value;
    }

}