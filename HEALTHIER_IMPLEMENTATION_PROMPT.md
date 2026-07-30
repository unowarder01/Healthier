# Промпт для ИИ-исполнителя: Healthier MVP

Ты — ведущий Kotlin Multiplatform-разработчик. Реализуй работоспособный MVP **Healthier** в текущем репозитории `/Users/unowarder01/Github/Healthier`. Работай как инженер, а не как генератор отдельных экранов: архитектура, навигация, данные, доступность, локализация, тесты и воспроизводимая сборка являются частью результата.

## 1. Контекст и источники истины

До первой правки полностью изучи следующие локальные источники:

1. `/Users/unowarder01/Downloads/healthier-mvp.pdf` — функциональные и визуальные требования MVP.
2. `/Users/unowarder01/Work/spin-id/ARCHITECTURE.md` — архитектурный ориентир. Заимствуй его принципы и границы модулей, но не копируй код, package names, устаревшие зависимости, exact версии или лишние для Healthier модули.
3. `/Users/unowarder01/Downloads/clean arch.jpg` — схема Clean Architecture, которую нужно соблюсти.
4. Текущее содержимое репозитория, включая `settings.gradle.kts`, `gradle/libs.versions.toml`, Gradle-модули, iOS host и существующий Ktor server.

Исходный шаблон уже нацелен на Android, iOS и web (JS/Wasm). MVP выпускается на Android и iOS с единым Compose Multiplatform UI; web не является продуктовой целью MVP, но не должен быть сломан. Архитектура и бизнес-логика должны позволять позже сделать web-клиент без копирования доменной логики.

Требования этого задания имеют приоритет над шаблоном. При конфликте с архитектурой из `spin-id` выбирай её принцип (слои, изоляция, UDF, composition root), адаптируя названия и конфигурацию к Healthier.

## 2. Режим работы и границы самостоятельности

1. Сначала проведи краткий audit стартового проекта и создай `docs/IMPLEMENTATION_DECISIONS.md`. В нём зафиксируй модульную схему, версии/совместимость библиотек, API-контракты, выбранного картографического провайдера, source sets и все допущения.
2. Затем реализуй MVP вертикальными срезами: foundation -> Splash/Auth -> выбор города и загрузка клиник -> Home/Health -> Map -> Profile. После каждого заметного среза собирай затронутый target и исправляй ошибки сразу.
3. Не жди уточнений для малых продуктовых деталей. Прими консервативное, обратимое допущение и внеси его в `IMPLEMENTATION_DECISIONS.md`.
4. Не выдумывай production-credentials, домены API, аккаунты соцсетей, юридические тексты, медицинские документы, рейтинги либо ссылки на социальные сети. Для этого используй конфигурацию, mock/debug-реализацию или явное состояние «не настроено».
5. Если реальная внешняя интеграция невозможна без секрета, приложение всё равно должно собираться и демонстрировать полный MVP-флоу в debug через внедряемый fake. В release нельзя имитировать успешную внешнюю авторизацию или реальную карту.
6. Не коммить, не пушь и не удаляй пользовательские файлы без прямого запроса. Не меняй внешние проекты, кроме чтения двух указанных reference-файлов.

## 3. Обязательный стек и управление зависимостями

Используй, а не только декларируй, следующий стек:

- Kotlin Multiplatform: `commonMain`, `androidMain`, `iosMain`, `webMain` и общий для Android+iOS source set **`nonWebMain`**;
- Kotlin, Compose Multiplatform / Jetpack Compose, Coroutines и Flow;
- Koin; Decompose + Essenty; FlowMVI;
- Ktor client (и Ktor server только для локального demo API, описанного ниже);
- Room + KSP для кэша Android+iOS;
- `com.russhwolf:multiplatform-settings` для несекретных настроек;
- Coil 3/KMP для изображений;
- `kotlinx-datetime` и `kotlinx.serialization.json`;
- Ultron для Android instrumented UI tests.

Правила зависимостей:

- Сначала проверь официальную документацию и release notes. Используй совместимые стабильные версии; не закрепляй alpha/beta/RC без объективной причины, записанной в decisions. Обновляй version catalog согласованно, не раскидывай версии по Gradle-файлам.
- Не придумывай API FlowMVI, Decompose, Room или Koin. Сверяй вызовы с фактически выбранной версией и компилируй каждый target.
- `commonMain` не импортирует Android, UIKit, Java, browser APIs или конкретные SDK карт/авторизации. Для небольших платформенных различий применяй `expect/actual`; для больших — common interface и platform-specific implementation, зарегистрированную через Koin.
- `nonWebMain` объединяет Android+iOS код: `commonMain <- nonWebMain <- androidMain/iosMain`. `webMain` объединяет JS/Wasm. Платформенный код не должен дублировать common UI или бизнес-логику.
- Ktor engines, Room database builders, secure storage, social sign-in, photo picker, URL launcher и map renderer должны быть зависимостями/actual только подходящих source sets.
- Секреты, tokens и API keys никогда не попадают в VCS, исходники, ресурсы, логи, analytics и UI state. Добавь безопасные примеры конфигурации (`local.properties`/environment, `.example` без значений) и обнови `.gitignore`, если нужно.

## 4. Архитектура и структура модулей

Применяй Clean Architecture и UDF. Схема потоков данных: пользовательский intent -> FlowMVI Store -> use case -> repository interface -> repository/data source -> DTO/entity; обратно только immutable domain/UI state и one-shot action. Composable не обращается к API, DAO, Settings, Koin или repository напрямую.

Сохрани `:app:shared` как KMP framework, ожидаемый существующим iOS host, но сделай его **application presentation root**, а не generic shared-модулем. Он владеет `App`, запуском Koin, Decompose root navigation, платформенными entry points и агрегацией feature composition modules. Не создавай generic `:shared` module.

Организуй core в отдельных Gradle-модулях. Мигрируй стартовый `:core` в эту структуру (например, его текущий общий код — в `:core:common`) и обнови все project dependencies; после миграции не оставляй второй конкурирующий generic core-модуль:

- `core:common`: `AppResult`, типизированные ошибки, dispatcher provider, базовые use case/mapper contracts, logger interface;
- `core:design-system`: theme, tokens, typography, colours, AppLogo, переиспользуемые поля/карточки/empty/loading/error states;
- `core:network`: Ktor client, JSON, timeouts, typed network error mapping, auth header abstraction;
- `core:database`: Room schema, database factory contract, migrations и cache DAO;
- `core:preferences`: typed wrapper над Multiplatform Settings для языка, темы и выбранного города;
- `core:mvi` и `core:presentation`: единые FlowMVI/Decompose conventions, lifecycle-safe state collection, navigation helpers;
- `core:platform`: contracts для secure storage, external URL, photo picker, social auth и карты, а также platform actuals.

Не добавляй core-модулю зависимостей на features или app. Не превращай `core` в свалку: код с одной продуктовой причиной изменения остаётся в feature.

Создай минимум следующие feature namespaces: `splash`, `auth`, `city`, `home`, `health`, `map`, `profile`. Для **каждой** feature обязательно создай ровно эти основные модули:

```text
:features:<feature>:domain
:features:<feature>:data
:features:<feature>:ui
:features:<feature>:composition
```

Дополнительный `ui-api` не создавай для MVP. Если общий код действительно нужен двум feature, сначала докажи в decisions два реальных потребителя и одну общую причину изменения; лишь затем допустим узко названный `:shared:<responsibility>` leaf, который не зависит от feature `data`/`ui`/`composition` и `:app:shared`.

Строго соблюдай зависимости:

| Модуль | Разрешено | Запрещено |
| --- | --- | --- |
| `domain` | core contracts, собственные domain, другие feature domain contracts при обосновании | data, ui, composition, app |
| `data` | собственный domain, core network/database/preferences/platform | feature data другого владельца, любой feature ui/composition, app |
| `ui` | собственный domain, presentation/design-system/mvi/platform core | любой data implementation, ui другого feature, app |
| `composition` | только свои `domain`, `data`, `ui` и Koin | бизнес-логика, UI implementation, core/app/другая feature |
| `:app:shared` | все composition modules и узкие публичные UI navigation contracts | feature data напрямую |

`composition` — только feature composition root: он включает Koin modules, объявленные владельцами слоёв. `:app:shared` загружает composition modules ровно один раз. Реализации feature `Navigator` находятся в app root, интерфейсы — в feature UI. Все зависимости передавай constructor injection; не используй service locator внутри Store, ViewModel, Component, repository или Composable.

### Экранный контракт

В каждом экранном пакете должны быть понятные, отдельные файлы (допустима внутренняя декомпозиция, но эти entry points обязательны):

```text
<Screen>MainScreen.kt
<Screen>ViewModel.kt
<Screen>Contract.kt
<Screen>Navigator.kt
<Screen>Component.kt
```

`Contract` определяет immutable `State`, user `Intent` и одноразовый `Action`. Единственный mutable presentation source — FlowMVI Store. `ViewModel` обязателен по имени, но не создаёт второй `StateFlow`, второй reducer или самостоятельный scope: это тонкая совместимая facade/owner над Store либо типизированная реализация, соответствующая реальному API FlowMVI. Предпочтительная цепочка: `Contract -> StoreFactory/ViewModel -> Store -> retained Component -> MainScreen`.

- `Component` получает `ComponentContext`, создаёт/retains Store с постоянным строковым ключом, маршрутизирует typed Action в `Navigator` и не хранит shared mutable state.
- Store name/key стабилен и семантичен, например `city.choose-city`; в ключах нет `hashCode`, `navigationId` и случайных UUID.
- Intent обрабатываются последовательно; состояние обновляется атомарно; screen work отменяется при уничтожении Component. Не создавай unmanaged `CoroutineScope`, не используй application scope для screen work.
- События навигации и сообщений — Actions/effects, а не поля «consumed» в State. Composable отправляет только intents и рендерит State.
- `@Composable` функции stateless насколько возможно, с state hoisting для reusable controls; state собирается lifecycle-aware на Android. UI-модели не являются DTO/Room entity/domain object, если это смешивает слои.

## 5. Навигация, стартовое состояние и локализация

Построй один Decompose root stack. Нормальный MVP-путь ровно такой:

```text
Splash -> Auth -> ChooseCity -> Home(Health | Map | Profile)
```

Не добавляй регистрацию, пароль, медицинские консультации, платежи, deep links, восстановление ранее авторизованной сессии или другие экраны, которых нет в MVP. Persist выбранного языка/темы/города для настроек, но на каждый cold start показывай Splash и следуй указанному MVP-переходу; не обходи Splash/Auth автоматически.

`Home` содержит BottomNavigation с тремя табами **Health**, **Map**, **Profile** и отдельный сохранённый child stack для каждого tab. По умолчанию открыт Health. Повторный tap по tab не должен дублировать destination. Android Back: сначала закрывает BottomSheet, затем возвращает к Health из другого root tab; из Health отдаёт управление системе. Переходы Splash->Auth, Auth->City, City->Home — горизонтальный slide, с корректным BackStack и без возможности двойной навигации при повторном tap.

Весь видимый текст вынеси в Compose Multiplatform resources. Обязательные локали: Georgian (`ka`), English (`en`), Russian (`ru`). Исходные тексты в PDF переводи для выбранного языка, не оставляй русский hard-coded. Текущий язык — единый app-level state из typed Settings; смена языка из toolbar/Profile немедленно обновляет весь Compose UI. Каждый флаг имеет текстовое semantics description; emoji не должны быть единственным способом передать смысл.

Поддержи light, dark и system theme. Описанные в PDF белые экраны — light theme. В dark theme используй эквивалентные surface/contrast tokens, а не белый hard-code.

## 6. UI и точные функциональные требования

Используй Material 3 как доступную основу, но реализуй свой компактный design system. Все экраны должны корректно учитывать safe areas, keyboard insets, маленькие и большие телефоны, font scale и минимум 48.dp touch targets. Добавь `testTag`/semantics для ключевых элементов. Используй контраст не ниже WCAG AA; изображения Coil обязаны иметь placeholder/error state и content description либо быть явно decorative.

Если бренд-цвета и готовый логотип не переданы, сделай нейтральный `AppLogo`: скруглённый `Box` c монограммой `H`; палитра, radius и shadow хранятся только в design tokens. Базовый radius для logo/card/search container — **16.dp**. Для светлых floating cards используй единый мягкий outer shadow (например elevation 8.dp, alpha около 12 %), не копируй shadow вручную на каждом экране.

### SplashScreen

- Белый light background; `AppLogo` 96.dp по центру экрана.
- Через **500 ms** после первого composition снизу появляется белый `fillMaxWidth` language container со скруглением 16.dp и outer shadow. Entrance: `slideInVertically` from full container height, 300 ms, FastOutSlowIn easing. Таймер запускай один раз, не при каждой recomposition.
- В контейнере три строки: Georgian, English, Russian. Слева — flag asset и локализованное название языка, справа — radio button.
- Выбор языка: выбранный radio dot делает bounce 220 ms; container уезжает вниз за 220 ms; logo одновременно исчезает fade за 180 ms. После завершения exit animation единоразово navigates slide-right-to-left на Auth. Заблокируй повторные клики во время ухода.

### AuthScreen

- Белый light background, AppLogo 56.dp вверху по центру.
- Через 500 ms title «Авторизация» (локализованно) въезжает сверху, auth container — снизу; оба 300 ms. Title остаётся визуально около центра, кнопки — в нижней части с системными insets.
- Container: `Column`, `fillMaxWidth`, horizontal/bottom margin 16.dp. Кнопки имеют единый вертикальный gap 12.dp и занимают минимум 48.dp высоты.
- iOS: Apple, Google, Meta, Telegram. Android: Google, Meta, Telegram. Apple button не существует в Android composition и не маскируется условной заглушкой.
- Визуально следуй приложенному примеру кнопок: Apple — официальный чёрный стиль, Google — официальный нейтральный стиль, Meta — иконка Meta и текст Meta (не Facebook), Telegram — его primary colour и иконка. Не скачивай случайные brand assets и не нарушай правила брендов; используй официальные SVG/vector assets либо локальные нейтральные placeholder assets с явной заменой перед release.
- После **действительно успешного** provider result, получив `authToken`, сохрани только безопасно и переходи slide на ChooseCity. Ошибка отмены/сети остаётся на Auth и показывается через accessible inline/snackbar error; disabled/loading состояние запрещает повторный вызов.
- Создай `SocialAuthProvider` contract и platform implementations. Реальные SDK запускай только с предоставленными client IDs/keys. В debug добавь dependency-injected `DemoSocialAuthProvider`, чтобы тестировать флоу без секретов. Release provider без configuration возвращает понятную ошибку и никогда не выдаёт фиктивный token. Token не клади в Multiplatform Settings: используй platform secure storage contract (Keychain/Android Keystore-backed storage) либо держи только в памяти до появления одобренного secure implementation.

### ChooseCityScreen

- Toolbar с локализованным «Ваш город».
- Основной scrollable список городов Грузии строго отсортирован по убыванию `population`; источник, дату и правило включения городов задокументируй. Храни `id`, названия для трёх локалей, aliases для поиска и population в data layer, не в Composable.
- Bottom search container плавает над списком: horizontal margin 12.dp, bottom margin 16.dp, radius 16.dp, outer shadow. Внутри TextField: horizontal padding 16.dp, vertical 8.dp, radius 16.dp, локализованный hint «Search». Список имеет bottom content padding, достаточный, чтобы ни одна строка не оказалась под контейнером.
- Поиск всегда применяет `query.trim()`. Пустая после trim строка показывает весь список; иначе совпадения по name и aliases case-insensitively. Если результатов нет, покажи локализованный empty state, центрированный в доступной области над search container.
- При tap по одному городу запускай use case получения клиник; только у этой строки показывай progress справа и отключай только повторный tap этой строки. Остальные строки не должны начать второй competing navigation. При ошибке убери progress, оставь экран и покажи retryable error. По успешной загрузке и кэшировании клиник сохрани выбранный city id и slide-навигацией перейди в Home.

### Home / HealthScreen

- `Home` — shell без продуктовой логики, BottomNavigation и табовые child stacks.
- Health сверху вниз: toolbar, search field, horizontal stories, top clinics, top doctors. Допускаются лаконичные локализованные section headers, но не добавляй неизвестные действия.
- Toolbar: `Row(fillMaxWidth)`, horizontal padding 16.dp; слева крупное «Health», справа доступные icon buttons смены location и language. Location открывает уже реализованный city selection flow/диалог и при успехе безопасно reloads data; language использует тот же language selection contract.
- Search: `fillMaxWidth`, horizontal/top margin 16.dp, hint «Клиника / доктор / процедура» (локализованный). До появления отдельного search-results screen фильтруй уже загруженные clinic/doctor/procedure данные локально с debounce 300 ms и `trim`; отображай корректный empty state. Не придумывай отдельную search navigation.
- Stories: `LazyRow(fillMaxWidth)` с верхним отступом 16.dp. Каждый item: Column(Image 64.dp + Text 12.sp), border 2.dp. Не используй внешние horizontal padding у LazyRow: вставь `Spacer(16.dp)` первым и последним item, чтобы список визуально уходил за края экрана.
- Top clinics: `LazyRow(fillMaxWidth)`, top margin 16.dp; card image и title с `fontSize=17.sp`, `FontWeight.Medium`. Между first/last card также ручные `Spacer(16.dp)`. При gap 12.dp вычисли card width из `BoxWithConstraints`, чтобы внутри viewport после боковых 16.dp были видны **1.25 карточки**: `(availableWidth - 32.dp - 12.dp) / 1.25`.
- Top doctors аналогичен, но text `13.sp`, `FontWeight.Normal` и ширина при двух видимых gaps: `(availableWidth - 32.dp - 24.dp) / 2.25`, то есть видно **2.25 карточки**. Не используй `LazyHorizontalGrid`: в спецификации нужен `LazyRow`.

### MapScreen

- Покажи выбранный один provider: **Google Maps** для Android+iOS. В `IMPLEMENTATION_DECISIONS.md` зафиксируй лицензирование, способ передачи API keys и точную версию/совместимость SDK. Не интегрируй одновременно Google и Yandex.
- Общие данные маркеров, состояние selection и clinic bottom sheet находятся в common UI/domain; native map view — только за `MapRenderer` contract и `expect/actual`/platform implementation. Не дублируй MapScreen для iOS и Android.
- Каждый маркер соответствует загруженной клинике выбранного города. Tap marker устанавливает selected clinic и открывает modal BottomSheet с минимумом доступных данных: изображение/placeholder, название, специализация, адрес, кнопка закрытия. Dismiss очищает selection. Нажатие по клинике не меняет выбранный город.
- API key читается только из неотслеживаемой platform configuration. При его отсутствии debug предоставляет явно обозначенный fake renderer с кликабельными маркерами; release показывает доступное error/unavailable state вместо ложной карты. Web использует безопасный unavailable renderer и продолжает компилироваться.

### ProfileScreen

- `LazyColumn`: toolbar, account card, documents, settings, social networks.
- Toolbar: как на Health, текст «Profile» локализован, справа location/language.
- Account card: круглый центрированный avatar 108.dp; ниже row из имени и фамилии provider/profile; pencil открывает edit bottom sheet. Edit позволяет изменить имя и аватар. Используй Android Photo Picker и iOS PHPicker через platform contract, копируй выбранный файл в app-owned storage, не проси broad storage permission. Если платформенный picker недоступен, control остаётся доступным и возвращает понятную ошибку. Профильный fake repository сохраняет name/avatar reference для demo; реальный backend не выдумывай.
- Documents — единая card/list style с document icon и chevron: «Удостоверения личности», «Согласия на обработку данных», «Анализы и сканирования». Так как legal content/storage/API не описаны, tap показывает non-destructive «Скоро будет» state; не реализуй загрузку, хранение или обработку медицинских документов.
- Settings — тот же list style, но без ведущей иконки: «Цветовая тема», «Язык приложения», «Уведомления». Theme/language открывают реальные selectors; notifications без разрешённого backend/permission flow показывают корректное состояние настройки и не притворяются включёнными.
- Social — document-list style с иконками и chevron: Telegram, WhatsApp, Instagram, Facebook. Открывай URL только из product configuration. Если URL не настроен, покажи non-destructive message, не придумывай ссылку.

## 7. Данные, сеть, хранение и безопасность

Сначала определи доменные модели и repository interfaces в `domain`. DTO, routes, API, entities, DAO, mappers и implementation — только в `data`. У каждой границы модели отдельны: `ClinicDto -> ClinicEntity -> Clinic` и UI mapper перед state; не пропускай DTO/Entity в UI.

В MVP реализуй следующие контракты:

- `CityRepository.observe/searchCities()` — локальный curated catalog;
- `ClinicRepository.getClinics(cityId, forceRefresh)` — `suspend`/Flow contract с cache-first поведением, явным `AppResult` и cancellation;
- `ProfileRepository` — demo local profile abstraction;
- `SettingsRepository` — language/theme/city preferences;
- `AuthRepository` — provider result/session abstraction, не зависящая от SDK UI.

Ktor client обязан иметь `ContentNegotiation` JSON, явные connect/request/socket timeouts, корректную cancellation propagation, минимум одну typed network error mapping strategy и redacted logging. Не глоти `Throwable`; отдельно обрабатывай cancellation. Base URL и debug/release environment инжектируются, не hard-code в common source.

Так как production backend contract не передан, создай в существующем `:server` минимальный локальный Ktor demo endpoint `GET /v1/cities/{cityId}/clinics` с versioned JSON response и статичными seed clinics. Документируй request/response в `docs/API_CONTRACT.md`. Client API соответствует этому контракту, а debug DI может подменять remote source без доступного сервера. Не объявляй этот demo server production API и не добавляй неописанные write endpoints.

Используй Room + KSP для cache clinics на Android+iOS: schema version, migrations и DAO tests обязательны. Settings хранят только language/theme/city и несекретный profile draft. Auth tokens и персональные/медицинские данные не пишутся в Room или обычные Settings. Никогда не логируй token, ФИО, avatar URI, документы или ответы auth provider.

## 8. Тесты, качество и проверка

Реализуй и запусти тесты пропорционально MVP:

- common unit tests для domain use cases, city search/sort, repository mapping/cache и error mapping;
- FlowMVI Store tests для Splash, Auth, City, Health search, Map selection и Profile edit: initial state, intent, loading, success, error, navigation action;
- Room migration/DAO tests там, где поддерживает target;
- Compose UI tests по test tags для language animation state, platform-specific auth buttons, city trim/empty/progress, tab switching, map bottom sheet и Profile sections;
- Android instrumented smoke/navigation tests через Ultron для Splash -> Auth -> City -> Home и одного tab/map scenario;
- проверку, что Android, iOS simulator и web target компилируются. Web может показывать unavailable map/auth implementation, но не должен зависеть от Android/iOS imports.

Перед передачей результата выполни доступные из этого репозитория эквиваленты следующих проверок (сначала посмотри реальные task names через `./gradlew tasks --all`):

```bash
./gradlew :app:androidApp:assembleDebug
./gradlew <common-and-android-unit-test-tasks>
./gradlew <iosSimulatorArm64-compile-or-test-task>
./gradlew :app:webApp:wasmJsBrowserDevelopmentWebpack
./gradlew <android-connected-or-instrumented-test-task>
```

Если iOS simulator, browser или connected Android отсутствует в окружении, не выдумывай успешный запуск: выполни максимум доступной compile/test проверки, укажи exact command, причину и воспроизводимый следующий шаг. Исправь все появившиеся warnings/error, относящиеся к своей работе; не подавляй их broad `@Suppress`.

## 9. Definition of Done и формат handoff

Работа считается завершённой, только когда:

1. Все описанные экраны и переходы существуют, доступны с чистого запуска и работают с debug seed data.
2. Android и iOS используют один Compose UI и shared domain/data/UI logic; platform code локален и минимален.
3. Обязательные четыре модуля есть у каждой feature, границы зависимостей соблюдены, а app не импортирует feature data напрямую.
4. Нет секретов, фальшивых release-authorizations, production-API выдумок, Android imports в common source или дублированных экранов iOS/Android.
5. Ресурсы локализованы на `ka`, `en`, `ru`; анимации не повторяются при recomposition; loading/error/empty/offline states не заблокированы.
6. Проверки из раздела 8 выполнены в максимально доступном объёме, а их результаты честно сообщены.

В финальном handoff дай кратко: выполненные вертикальные срезы, дерево добавленных модулей, изменённые ключевые файлы, команды и результаты проверок, внешние конфигурации, которые ещё должен предоставить владелец (OAuth IDs/secrets, map key, production API URL, social URLs), и ссылку на `docs/IMPLEMENTATION_DECISIONS.md`. Не пиши общих обещаний и не скрывай ограничения.
