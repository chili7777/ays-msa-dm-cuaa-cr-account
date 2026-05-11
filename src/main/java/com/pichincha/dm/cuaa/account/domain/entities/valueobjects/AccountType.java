package com.pichincha.dm.cuaa.account.domain.entities.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class AccountType implements ValueObject<String> {

    @Getter
    private final String value;

    public AccountType(String value) {
        ensureValidAccountType(value);
        this.value = value;
    }

    private void ensureValidAccountType(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }
        if (!isValidType(value)) {
            throw new IllegalArgumentException(
                    "Invalid account type: " + value + ". Allowed types: SAVINGS, CHECKING, BUSINESS"
            );
        }
    }

    private boolean isValidType(String type) {
        return type.equals("SAVINGS") || type.equals("CHECKING") || type.equals("BUSINESS");
    }

    @Override
    public String toString() {
        return value;
    }
}