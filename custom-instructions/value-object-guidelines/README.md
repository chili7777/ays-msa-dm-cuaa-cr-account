# Value Object Guidelines

This folder documents the implementation strategy for Value Objects in the account domain.

## Scope

- Type-safe identifiers (`ClientId`, `AccountId`, etc.)
- Encapsulation of primitive types
- Validation at construction time
- Immutability with Lombok

## Related files

- `src/main/java/com/pichincha/dm/cuaa/account/domain/entities/identifiers/Identifier.java`
- `src/main/java/com/pichincha/dm/cuaa/account/domain/entities/identifiers/ClientId.java`

## Notes

- All value objects inherit from `Identifier` abstract base.
- UUID values are stored as strong-typed identifiers, never raw primitives.
- Equals and hashCode are Lombok-generated; validation ensures correctness.