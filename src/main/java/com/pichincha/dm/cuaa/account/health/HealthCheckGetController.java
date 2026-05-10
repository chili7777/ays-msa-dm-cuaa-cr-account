package com.pichincha.dm.cuaa.account.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Exposes a lightweight health-check endpoint that confirms the service is reachable and responsive, returning a stable status payload for runtime monitoring and deployment smoke tests.
 */
@RestController
public class HealthCheckGetController {

    private static final String STATUS_KEY = "status";
    private static final String STATUS_OK = "ok ✅";
    private static final Map<String, String> HEALTH_RESPONSE = Map.of(STATUS_KEY, STATUS_OK);

    @GetMapping("/health-check")
    public Mono<Map<String, String>> getHealthCheck() {
        return Mono.just(HEALTH_RESPONSE);
    }
}