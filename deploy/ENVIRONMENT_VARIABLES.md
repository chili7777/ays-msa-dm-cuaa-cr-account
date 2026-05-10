# Required environment variables

## Common

- `AYS_MSA_CR_ACCOUNT_PROFILE` (`development`, `staging`, `production`)
- `AYS_MSA_CR_ACCOUNT_PORT` (default `8080`)

## Naming convention applied in this repository

- `AYS` -> project acronym adopted from the repository prefix
- `MSA` -> microservice tool type
- `CR_ACCOUNT` -> component/domain name
- `PROFILE` / `PORT` -> configured attribute


## Branch to environment mapping

- `feature/*` -> `staging`
- `develop` -> `development`
- `main`/`master` -> `production`