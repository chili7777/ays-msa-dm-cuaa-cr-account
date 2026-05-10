package com.pichincha.dm.cuaa.account.health;

import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import org.junit.jupiter.api.Test;

/**
 * Validates health-check endpoint behavior through HTTP requests, ensuring status contract stability and quick feedback when runtime or serialization changes affect external service observability.
 */
class HealthCheckGetControllerTest extends RequestTestCase {

    @Test
    void given_applicationIsRunning_when_getHealthCheck_then_returnStatusOkWithEmoji() throws Exception {
        assertResponse("/health-check", 200, "{ \"status\": \"ok ✅\" }");
    }
}