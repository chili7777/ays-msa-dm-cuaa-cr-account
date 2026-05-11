package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.Value;

/**
 * Value object representing a customer's physical address.
 * Encapsulates address details and ensures that a valid location is provided.
 */
@Value
public class Address implements ValueObject<String> {

    String value;

    public Address(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Address must not be null or empty");
        }
        this.value = value;
    }

}