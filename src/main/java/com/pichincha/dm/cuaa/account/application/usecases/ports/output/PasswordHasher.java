package com.pichincha.dm.cuaa.account.application.usecases.ports.output;

public interface PasswordHasher {
    String hash(String password);
    boolean matches(String rawPassword, String hashed);
}