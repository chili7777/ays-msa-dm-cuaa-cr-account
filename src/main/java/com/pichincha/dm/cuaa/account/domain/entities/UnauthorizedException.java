package com.pichincha.dm.cuaa.account.domain.entities;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}