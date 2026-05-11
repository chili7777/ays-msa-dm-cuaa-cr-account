package com.pichincha.dm.cuaa.account.shared.objectmothers;

import org.springframework.http.HttpHeaders;

public final class HttpHeadersMother {

    private static final String X_GUID_HEADER     = "x-guid";
    private static final String X_APP_HEADER      = "x-app";
    // OpenAPI: @Size(max = 50) on x-app header
    private static final int    X_APP_MAX_LENGTH  = 50;

    private HttpHeadersMother() {
    }

    public static HttpHeaders random() {
        return create(
                UuidMother.randomAsString(),
                FakerMother.faker().app().name()
        );
    }

    public static HttpHeaders create(String xGuid, String xApp) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_GUID_HEADER, xGuid);
        headers.add(X_APP_HEADER, truncate(xApp, X_APP_MAX_LENGTH));
        return headers;
    }

    private static String truncate(String value, int maxLength) {
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }
}