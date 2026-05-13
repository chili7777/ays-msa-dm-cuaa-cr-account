package com.pichincha.dm.cuaa.account.infrastructure.dataprovider;

import com.pichincha.dm.cuaa.account.application.usecases.ports.output.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String hashed) {
        return passwordEncoder.matches(rawPassword, hashed);
    }
}