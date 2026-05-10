package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class CuentasPostControllerTest extends RequestTestCase {

    @Test
    void given_validAccountCreateRequest_when_createAccount_then_returnCreatedStatus() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("x-guid", "b88f8ab7-133f-4598-932f-42f5d8fc0ed4");
        requestHeaders.add("x-app", "ays-account-tests");

        String validAccountCreateRequestBody = """
                {
                  "clienteId": "d7f1f2f8-3a64-4305-a7ce-d9f06174bcb5",
                  "numeroCuenta": "ACC-999-ALPHA-42",
                  "tipoCuenta": "AHORRO",
                  "saldoInicial": 157.89,
                  "estado": true
                }
                """;

        assertRequestWithBody("POST", "/cuentas", validAccountCreateRequestBody, 201, requestHeaders);
    }
}