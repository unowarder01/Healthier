# Healthier

Kotlin Multiplatform Compose application with Android, iOS and web hosts.

## Features

- `features:splash`
- `features:onboarding`
- `features:auth`

The application flow is `Splash → Onboarding → Auth`.

## Modules

- `app:androidApp`, `app:shared`, `app:webApp`, `app:iosApp`
- `core:common`, `core:design-system`, `core:mvi`, `core:presentation`
- `features:{splash,onboarding,auth}:{ui,composition}`
- `server` — empty Ktor application stub

`domain` and `data` directories in the three remaining features are intentionally empty.
`core/network`, `core/database` and `core/preferences` are intentionally empty; `core/platform`
has been removed.

## Build

```bash
./gradlew :app:androidApp:assembleDebug
./gradlew :app:shared:compileKotlinIosSimulatorArm64
./gradlew :app:webApp:compileKotlinJs
./gradlew :server:test
```
