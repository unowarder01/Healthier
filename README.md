# Healthier

Kotlin Multiplatform MVP for Android, iOS and web with one Compose UI, Decompose navigation,
FlowMVI stores, Koin composition, Ktor networking and Room clinic cache on Android/iOS.

## Product flow

```text
Splash / language → Social auth → Choose city → Home
                                               ├── Health
                                               ├── Map
                                               └── Profile
```

Debug builds use deterministic demo auth and clinic data, so the complete flow is available
without OAuth secrets or a running backend. Release builds never fabricate authorization:
unconfigured integrations return a visible `NotConfigured` state.

## Modules

- `app:androidApp`, `app:shared`, `app:webApp`, `app:iosApp`
- `core:{common,design-system,network,database,preferences,mvi,presentation,platform}`
- `features:{splash,auth,city,home,health,map,profile}:{domain,data,ui,composition}`
- `server` — local demo Ktor endpoint only

Architecture decisions and compatibility notes are in
[`docs/IMPLEMENTATION_DECISIONS.md`](docs/IMPLEMENTATION_DECISIONS.md). The local server contract
is in [`docs/API_CONTRACT.md`](docs/API_CONTRACT.md).

## Local configuration

Copy the keys you need from `config.example.properties` into the untracked `local.properties`.
Android reads `HEALTHIER_API_BASE_URL` into `BuildConfig`; iOS reads the same key from Info.plist
(usually supplied by an xcconfig). An empty value is intentional and produces `NotConfigured`
outside debug demo mode.

Owner-supplied configuration is still required for production:

- production API URL;
- Google/Meta/Telegram/Apple OAuth IDs and their native SDK configuration;
- restricted Google Maps keys and native SDK setup for Android/iOS;
- official social/support URLs.

Do not commit these values.

## Build and test

```bash
./gradlew :app:androidApp:assembleDebug
./gradlew :app:shared:compileKotlinIosSimulatorArm64
./gradlew :app:webApp:jsBrowserDevelopmentWebpack
./gradlew :app:webApp:wasmJsBrowserDevelopmentWebpack
./gradlew :server:test
```

Common/data/Store and Room tests run on the iOS simulator target:

```bash
./gradlew \
  :core:network:iosSimulatorArm64Test \
  :core:database:iosSimulatorArm64Test \
  :features:city:data:iosSimulatorArm64Test \
  :features:splash:ui:iosSimulatorArm64Test \
  :features:auth:ui:iosSimulatorArm64Test \
  :features:city:ui:iosSimulatorArm64Test \
  :features:health:ui:iosSimulatorArm64Test \
  :features:map:ui:iosSimulatorArm64Test \
  :features:profile:ui:iosSimulatorArm64Test
```

The Ultron test APK is built with:

```bash
./gradlew :app:androidApp:assembleDebugAndroidTest
```

With an emulator or device connected:

```bash
./gradlew :app:androidApp:connectedDebugAndroidTest
```

Run the local demo API with `./gradlew :server:run`. Run web interactively with
`./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`. Open `app/iosApp` in Xcode to run iOS.
