# Current architecture

`app:shared` owns the Decompose root stack and initializes Koin once. The only application
destinations are Splash, Onboarding and Auth.

The active core modules are:

- `core:common` — `AppLanguage` only.
- `core:design-system` — Compose theme and reusable UI elements.
- `core:mvi` — FlowMVI support.
- `core:presentation` — component and lifecycle support.

Each remaining feature has only `ui` and `composition` Gradle modules. Its nested `domain` and
`data` directories are intentionally empty, reserving the locations for future work without
shipping domain models, data models, repositories or platform adapters.

`core:network`, `core:database` and `core:preferences` are empty placeholders. `core:platform`
and the City, Health, Map and Profile features have been removed.
