# Controller TDD Template

Use this template for new endpoints.

## 1) Test first (Red)

```java
class <ControllerName>Test extends RequestTestCase {

    @Test
    void given_<precondition>_when_<action>_then_<expectedResult>() throws Exception {
        String requestBody = """
                {
                  "field": "value"
                }
                """;

        assertRequestWithBody("POST", "<endpoint>", requestBody, <statusCode>);
    }
}
```

Run:

```bash
./gradlew test --tests "*<ControllerName>Test"
```

## 2) Minimal implementation (Green)

```java
@RestController
public class <ControllerName> {

    @<MappingAnnotation>("<endpoint>")
    public Mono<Map<String, String>> <methodName>() {
        return Mono.just(Map.of("<key>", "<value>"));
    }
}
```

## 3) Refactor

- Extract constants
- Improve naming
- Keep response contract unchanged
- Re-run tests

## Commit sequence example

```text
test(<scope>): add failing <endpoint> contract test
feat(<scope>): implement <endpoint> endpoint
refactor(<scope>): improve <endpoint> implementation
```

The first commit in the sequence must be created right after confirming the test fails.