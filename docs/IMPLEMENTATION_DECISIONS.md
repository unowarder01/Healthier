# Healthier MVP - implementation decisions

## Audit

The repository started as the JetBrains KMP wizard template with Android, iOS, JS/Wasm, and
Ktor server targets. It contained one generic `:core`, a single shared Compose screen, no
navigation, no persistence, no dependency injection, and no tests. The old `:core` is migrated
to responsibility-specific core modules and is not retained as a competing generic module.

The workspace has no `.git` metadata, so the implementation cannot report a Git diff or commit
state. Existing user-owned files outside this repository are read-only references.

## Module graph

`app:shared` is the application presentation root. It depends on feature composition modules and
feature UI contracts, owns the Decompose root stack and navigator implementations, initializes
Koin once, and exports the common Compose UI to Android/iOS/web hosts.

Core modules:

- `core:common`: results, typed errors, dispatchers, mapper/use-case/logger contracts.
- `core:design-system`: theme, tokens, logo, reusable states and controls.
- `core:network`: Ktor client, environment, redacted logging and typed failures.
- `core:database`: Room schema and platform database builders for Android/iOS.
- `core:preferences`: typed Settings-backed language/theme/city preferences.
- `core:mvi`: FlowMVI conventions and stable store identities.
- `core:presentation`: Decompose/Essenty lifecycle and navigation helpers.
- `core:platform`: social auth, secure storage, map, picker and URL-launcher contracts.

Every feature (`splash`, `auth`, `city`, `home`, `health`, `map`, `profile`) has exactly
`domain`, `data`, `ui`, and `composition`. Composition modules contain Koin aggregation only.
There is no `ui-api` or generic shared module.

## Source sets

All KMP library modules use:

```text
commonMain
├── nonWebMain
│   ├── androidMain
│   └── iosMain
└── webMain
    ├── jsMain
    └── wasmJsMain
```

The web target keeps domain and presentation logic but uses unavailable platform adapters for
map/social auth/picker. Android and iOS use the same Compose screens.

## Versions and compatibility

The version catalog is the single version source. Kotlin 2.4.10, AGP 9.3.1, Gradle 9.6.1,
Compose Multiplatform 1.11.1, Ktor 3.5.1 and Android SDK 37 form the build baseline.
Stable versions selected on 2026-07-30 are FlowMVI 3.2.1, Decompose 3.5.0,
Essenty 2.5.0, Koin 4.2.2, Room 2.8.4, SQLite 2.6.2, KSP 2.3.10, Settings 1.3.0,
Coil 3.5.0, coroutines 1.11.0, serialization 1.11.0, datetime 0.8.0 and Ultron 2.6.3.
JetBrains Lifecycle is pinned to stable 2.11.0; Kotlin browser wrappers 2026.7.7 and Logback
1.6.1 are used by the web/server hosts.

Material 3 1.11.0-alpha07 is retained because it is the Compose Multiplatform-compatible
artifact supplied by the current wizard baseline; replacing it with an older stable artifact
would conflict with Compose 1.11.1. No product behavior depends on experimental Material APIs.

Room follows the official KMP setup: common entities/DAO/database, KSP for Android and both iOS
targets, `BundledSQLiteDriver`, versioned schema export, explicit `1 → 2` migration, DAO and
migration tests, and platform-only builders. SQLite remains at Room's resolved-compatible 2.6.2
instead of independently forcing 2.7.0.

## API and environments

The only server endpoint is the local demo `GET /v1/cities/{cityId}/clinics`, documented in
`docs/API_CONTRACT.md`. Debug defaults to injectable seed data so the full flow works with no
server. `HEALTHIER_API_BASE_URL`/platform build configuration can override the base URL.
No production API domain is assumed.

Authentication is provider-agnostic. Debug uses an explicitly named demo provider and stores its
short-lived demo token only through the secure-storage contract. Release without OAuth
configuration returns `NotConfigured` and never fabricates success. OAuth IDs and provider SDKs
remain owner-supplied configuration.

## Map provider and secrets

Google Maps is the sole selected native provider for Android and iOS. Its SDK terms require an
owner-controlled Google Cloud project, billing/terms acceptance, platform-restricted API keys and
correct attribution. Keys are read from untracked platform configuration (`local.properties` on
Android and `Config.xcconfig`/Info.plist on iOS); they are never stored in common sources.
Until keys and native SDK wiring are supplied, debug uses a clearly labeled, interactive fake
renderer and release/web shows an accessible unavailable state. The native SDK version is
intentionally not fabricated; it must be pinned when the owner supplies keys and accepts the SDK
license.

## Product assumptions

- City catalog is a curated MVP subset of incorporated Georgian cities, based on the 2014
  Georgian census city populations and ordered strictly by stored population descending.
  Catalog revision date: 2026-07-30. Aliases cover Georgian, English and Russian spellings.
- Clinic/doctor/story content is explicitly demo seed content; no ratings or medical claims are
  invented.
- Cold start always begins at Splash, regardless of persisted city/language/theme.
- Social URLs, legal documents, notifications backend, production profile/backend and medical
  document storage are not configured. Their controls show localized, non-destructive messages.
- Demo profile data is non-sensitive and local. Auth tokens, medical documents and personal data
  never enter Room or ordinary Settings.
