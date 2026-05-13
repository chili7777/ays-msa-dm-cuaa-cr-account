package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's role.
 * Helps the frontend manage screen visibility.
 */
@Value
public class Role implements ValueObject<String> {

    String value;

    public Role(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role must not be null or empty");
        }
        this.value = value;
    }

}