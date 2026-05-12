package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Mono<ResponseEntity<Object>> index() {
        return Mono.just(ResponseEntity.ok(Map.of(
                "name", "ays-msa-dm-cuaa-cr-account",
                "description", "Account Microservice",
                "version", "0.0.1-SNAPSHOT",
                "health_check", "/health-check",
                "docs", "/swagger-ui.html"
        )));
    }
}