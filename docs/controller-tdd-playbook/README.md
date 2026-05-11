# Controller TDD Playbook

This folder documents how to implement any new controller using TDD in this repository.

## Goal

Keep each endpoint implementation predictable, test-driven, and traceable through clear Conventional Commit messages.

## Standard flow for each endpoint

### 1) Red - start with a failing test

1. Add/extend an integration test class under `src/test/java` for the endpoint contract.
2. Validate:
   - HTTP status code
   - response body JSON
   - headers if needed
3. Run only the new test and confirm it fails.

Important:
- The first commit in the TDD flow is created in this RED phase.
- This commit must include only the failing acceptance test and required test helpers.
- Do not add controller implementation in this first commit.

Suggested commit type:

```text
test(<scope>): add failing <endpoint> contract test
```

### 2) Green - implement the minimum code

1. Add the controller method (`@GetMapping`, `@PostMapping`, etc.).
2. Return only what is needed for the test to pass.
3. Run targeted tests, then run full `test` task.

Suggested commit type:

```text
feat(<scope>): implement <endpoint> endpoint
```

### 3) Refactor - improve without changing behavior

1. Clean names, constants, and duplicated code.
2. Keep API contract and output exactly the same.
3. Re-run tests to guarantee no regressions.

Suggested commit type:

```text
refactor(<scope>): improve <endpoint> implementation
```

## Test base class recommendation

Use a reusable base similar to `RequestTestCase` to centralize common assertions and avoid duplicated WebTestClient setup.

## Commit checklist (Conventional Commits)

- type is present (`test`, `feat`, `refactor`, etc.)
- scope is clear and consistent
- description is imperative and lowercase
- first line is <= 72 chars
- no trailing period in first line

## Example used in this project

Endpoint: `GET /health-check`

1. `test(health-check): add failing endpoint contract test`
2. `feat(health-check): return ok status on endpoint`
3. `refactor(health-check): simplify response mapping`