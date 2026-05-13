package com.pichincha.dm.cuaa.account.domain.entities;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}