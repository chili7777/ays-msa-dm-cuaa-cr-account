package com.pichincha.dm.cuaa.account.domain.entities;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}