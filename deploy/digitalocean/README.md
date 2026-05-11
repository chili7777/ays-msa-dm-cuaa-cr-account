# DigitalOcean App Platform setup

This folder contains ready-to-import App Platform specs for each environment.

## Files
- `app-development.yaml`
- `app-staging.yaml`
- `app-production.yaml`

## Before importing
Replace `CHANGE_ME_GHCR_OWNER` in each file with your GitHub user or organization that owns the package:

- `ghcr.io/<owner>/ays-msa-dm-cuaa-cr-account`

Make sure the GHCR package is public, or add registry credentials in DigitalOcean before deploying a private package.

## How to create the apps
### Option 1: DigitalOcean UI
1. Go to App Platform.
2. Create a new app.
3. Choose Import App Spec.
4. Do not choose GitHub source with a fixed branch like `main` for this setup.
5. Paste the content of the corresponding file.
6. Save the app and copy its App ID.

### Option 2: doctl
```bash
doctl apps create --spec deploy/digitalocean/app-staging.yaml
doctl apps create --spec deploy/digitalocean/app-development.yaml
doctl apps create --spec deploy/digitalocean/app-production.yaml
```

## Required GitHub secrets
After creating the apps, add these GitHub Actions secrets:
- `DIGITALOCEAN_ACCESS_TOKEN`
- `DO_APP_ID_STAGING`
- `DO_APP_ID_DEVELOPMENT`
- `DO_APP_ID_PRODUCTION`

## Tag mapping expected by the workflow
- `feature/*` -> `staging-latest` (deployed to DigitalOcean)
- `develop` -> `development-latest` (just built, no cloud deploy)
- `main` or `master` -> `production-latest` (just built, no cloud deploy yet)

DigitalOcean only consumes the `staging-latest` tag currently. Branch rules live in GitHub Actions, not in App Platform.

## Spring Boot Profiles
Each environment has a corresponding Spring Boot profile in `src/main/resources/`:
- `application-staging.yaml`
- `application-development.yaml`
- `application-production.yaml`

The app reads the profile from the `AYS_MSA_CR_ACCOUNT_PROFILE` environment variable.