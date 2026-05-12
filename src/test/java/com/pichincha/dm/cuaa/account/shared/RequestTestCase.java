package com.pichincha.dm.cuaa.account.shared;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class RequestTestCase {

    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUpWebTestClient() {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    protected void assertResponse(String endpoint,
                                  Integer expectedStatusCode,
                                  String expectedResponse) throws Exception {

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(endpoint)
                .exchange()
                .expectStatus()
                .isEqualTo(expectedStatusCode);

        if (expectedResponse.isEmpty()) {
            responseSpec.expectBody().isEmpty();
        } else {
            responseSpec.expectBody().json(expectedResponse);
        }
    }

    protected void assertResponse(String endpoint,
                                  Integer expectedStatusCode,
                                  String expectedResponse,
                                  HttpHeaders headers) throws Exception {

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus()
                .isEqualTo(expectedStatusCode);

        if (expectedResponse.isEmpty()) {
            responseSpec.expectBody().isEmpty();
        } else {
            responseSpec.expectBody().json(expectedResponse);
        }
    }

    protected void assertRequestWithBody(String method,
                                         String endpoint,
                                         String body,
                                         Integer expectedStatusCode) throws Exception {

        webTestClient.method(HttpMethod.valueOf(method))
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), String.class)
                .exchange()
                .expectStatus()
                .isEqualTo(expectedStatusCode);
    }

    protected byte[] assertRequestWithBody(String method,
                                         String endpoint,
                                         String body,
                                         Integer expectedStatusCode,
                                         HttpHeaders headers) throws Exception {

        WebTestClient.ResponseSpec responseSpec = webTestClient.method(HttpMethod.valueOf(method))
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), String.class)
                .exchange();

        responseSpec.expectStatus().isEqualTo(expectedStatusCode);

        if (expectedStatusCode == 204) {
            responseSpec.expectBody().isEmpty();
            return new byte[0];
        }

        byte[] response = responseSpec.expectBody().returnResult().getResponseBody();
        return response != null ? response : new byte[0];
    }

    protected void assertRequest(String method,
                                 String endpoint,
                                 Integer expectedStatusCode) throws Exception {

        webTestClient.method(HttpMethod.valueOf(method))
                .uri(endpoint)
                .exchange()
                .expectStatus()
                .isEqualTo(expectedStatusCode);
    }

    protected void assertRequest(String method,
                                 String endpoint,
                                 Integer expectedStatusCode,
                                 HttpHeaders headers) throws Exception {

        webTestClient.method(HttpMethod.valueOf(method))
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus()
                .isEqualTo(expectedStatusCode);
    }
}