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

### Google Maps

Enable billing, **Maps SDK for Android**, and **Maps SDK for iOS** in the Google Cloud project. Use separate restricted API keys for each platform:

- Android key: restrict it to the `unowarder01.healthier` package and the signing certificate SHA-1, then add it to the ignored root `local.properties`:

  ```properties
  HEALTHIER_GOOGLE_MAPS_ANDROID_KEY=your_android_key
  ```

- iOS key: restrict it to the app bundle identifier, copy `app/iosApp/Configuration/Local.example.xcconfig` to `Local.xcconfig`, and set:

  ```text
  GOOGLE_MAPS_API_KEY=your_ios_key
  ```

The Android key is exposed to the manifest through the Google Maps Platform Secrets Gradle Plugin. The iOS SDK is pinned through Swift Package Manager; its key is passed to `GMSServices` by the iOS composition root. Neither local key file is tracked by Git.

The shared map screen lives in `nonWebMain`, while `androidMain` and `iosMain` contain the native rendering adapters. Web keeps its existing empty-state implementation.

Official setup references: [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/start), [Maps Compose](https://developers.google.com/maps/documentation/android-sdk/maps-compose), and [Maps SDK for iOS](https://developers.google.com/maps/documentation/ios-sdk/config).

### Commands

```bash
./gradlew :app:androidApp:assembleDebug
./gradlew :app:shared:compileKotlinIosSimulatorArm64
./gradlew :app:webApp:compileKotlinJs
./gradlew :server:test
```
