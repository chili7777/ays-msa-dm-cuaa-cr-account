# Custom Instruction: Value Objects (Type-Safe Identifiers)

Use this instruction when creating type-safe wrappers for business identifiers.

## Objective

- Replace raw UUIDs/strings with strongly-typed Value Objects.
- Encapsulate validation logic at construction time (fail fast).
- Leverage Lombok for immutability and equality contracts.

## Mandatory rules

1. All identifiers inherit from `Identifier` abstract base.
2. Value Objects are immutable records or Lombok POJO with `@Value`.
3. Validation occurs in constructor via `ensureValid...()` method.
4. Use Lombok `@Value` and `@EqualsAndHashCode` for contracts.
5. Private `value` field; expose via `getValue()` method.
6. English-only naming for classes, methods, and validation messages.

## Structure example: `ClientId`

```java
package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import java.util.UUID;
import lombok.Value;

@Value
public class ClientId extends Identifier {

    public ClientId(String value) {
        super(value);
        ensureValidUuid(value);
    }

    private void ensureValidUuid(String value) {
        UUID.fromString(value); // throws if invalid
    }
}
```

## Base class: `Identifier` abstract

```java
package com.pichincha.dm.cuaa.account.domain.entities.identifiers;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class Identifier {

    @Getter
    private final String value;
}
```

## Naming convention

- Abstract base: `Identifier`
- Concrete subclass: `{EntityName}Id` (e.g., `ClientId`, `AccountId`, `TransactionId`)

## Anti-patterns to avoid

- Allowing null or empty UUIDs at construction.
- Exposing raw `value` field; always use `getValue()`.
- Mixing strong types with raw UUIDs in the same aggregate.
- Skipping validation; rely on fail-fast principle.

## Integration with domain entities

Domain entities receive Value Objects, not raw strings:

```java
public record Account(ClientId clientId,
                      String accountNumber,
                      String accountType,
                      Double initialBalance,
                      Boolean status) {
}
```

## Mapper mapping for Value Objects

MapStruct mappers handle conversion automatically if the target type accepts a String constructor:

```java
@Mapping(target = "clientId", source = "clientId")
Account toAccount(AccountCreateRequestDto dto);
```

MapStruct will invoke `new ClientId(dto.getClientId().toString())` internally.