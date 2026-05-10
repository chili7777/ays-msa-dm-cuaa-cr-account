# Deploy Quickstart

## Branch strategy
- `feature/*` -> staging
- `develop` -> development
- `main`/`master` -> production

## Local docker test
1. Build jar
   ```bash
   ./gradlew clean test bootJar
   ```
2. Create your local env file from the corresponding example
   ```bash
   cp deploy/.env.staging.example deploy/.env.staging
   ```
3. Run one environment
   ```bash
   docker compose -f deploy/docker-compose.yml --env-file deploy/.env.staging up --build -d
   ```
   ```bash
   docker compose -f deploy/docker-compose.yml --env-file deploy/.env.development up --build -d
   ```
   ```bash
   docker compose -f deploy/docker-compose.yml --env-file deploy/.env.production up --build -d
   ```
4. Check endpoint
   - `http://localhost:8080/health-check`

## DigitalOcean App Platform setup
1. Use the specs under `deploy/digitalocean/` to create three apps.
2. Create each app from an App Spec or container image source, not from a GitHub branch source.
3. If App Platform asks you to choose a branch such as `main`, go back and choose the image-based flow instead.
4. Branch mapping stays in GitHub Actions:
   - `feature/*` -> publishes `staging-latest`
   - `develop` -> publishes `development-latest`
   - `main` or `master` -> publishes `production-latest`
5. In each app set HTTP port `8080`.
6. In each app set health check path `/health-check`.
7. In each app set environment variables from `deploy/ENVIRONMENT_VARIABLES.md`.
8. Use values from:
   - development: `deploy/.env.development.example`
   - staging: `deploy/.env.staging.example`
   - production: `deploy/.env.production.example`
9. In App Platform define at least:
   - `AYS_MSA_CR_ACCOUNT_PROFILE`
   - `AYS_MSA_CR_ACCOUNT_PORT`
10. Replace `CHANGE_ME_GHCR_OWNER` in `deploy/digitalocean/app-*.yaml` before importing the specs.
11. Ensure the GHCR package is public, or configure registry credentials in DigitalOcean if you keep it private.

## Git Flow compatibility
- Merge `feature/*` into `develop` when your changes are ready for integration.
- Merge `develop` into `main` or `master` only when you want production.
- DigitalOcean does not need to watch branches directly in this setup.
- GitHub Actions builds the image and triggers the correct app deployment based on the branch that receives the push.

## GitHub Actions secrets
Set these repository secrets:

- `DIGITALOCEAN_ACCESS_TOKEN`
- `DO_APP_ID_STAGING`
- `DO_APP_ID_DEVELOPMENT`
- `DO_APP_ID_PRODUCTION`

The workflow builds/tests, publishes an image to GHCR, and triggers deployment for the mapped environment.