package com.pichincha.dm.cuaa.account.health;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HealthCheckGetController {

    @GetMapping("/health-check")
    public Mono<Map<String, String>> index() {
        return Mono.just(Collections.singletonMap("status", "ok"));
    }
}