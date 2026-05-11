package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class AccountNumber implements ValueObject<String> {

    @Getter
    private final String value;

    public AccountNumber(String value) {
        ensureValidAccountNumber(value);
        this.value = value;
    }

    private void ensureValidAccountNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (value.length() < 10) {
            throw new IllegalArgumentException("Account number must be at least 10 characters long");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}