package com.pichincha.dm.cuaa.account.health;

import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import org.junit.jupiter.api.Test;

class HealthCheckGetControllerTest extends RequestTestCase {

    @Test
    void given_applicationIsRunning_when_getHealthCheck_then_returnStatusOk() throws Exception {
        assertResponse("/health-check", 200, "{ \"status\": \"ok\" }");
    }
}