# budinv

A personal budget and expense tracking application — available as a web app and a native Android app.

## What it does

- Maintain a **Ledger** for a date range, containing income and expense entries
- Organize expenses into a two-level hierarchy: **ExpenseType** → **ExpenseConcept** (e.g. *Transport* → *Car maintenance*)
- Define a **Budget** with planned amounts for three expense kinds:
  - **Regular** — known, recurring expenses (rent, subscriptions)
  - **Irregular** — known but infrequent (insurance renewals, taxes)
  - **Variable** — day-to-day discretionary spending
- Calculate income, total spending, balance, and budget deviation for any period

## Architecture

The monorepo contains six modules: five Maven modules for the backend and web frontend, and one Gradle module for the Android app. The Android module is a sibling directory — it is not part of the Maven build.

### Backend & web (Maven)

| Module | Contents |
|---|---|
| `budinv-model` | Domain model (`domain/` package) and business logic |
| `budinv-controller` | Spring MVC REST controllers, DTOs, and Java-based `@Configuration` classes |
| `budinv-rest-services` | WAR packaging and servlet configuration |
| `budinv-infrastructure` | JPA entities, Spring Data repositories, and Java-based persistence configuration |
| `budinv-web` | AngularJS + Bootstrap frontend |

Spring configuration is Java-based (`RestConfig`, `PersistenceConfig`) — there are no XML application context files.

### Android (`budinv-android/`)

Native Android app built with Kotlin and Jetpack Compose. Calls the same REST API as the web frontend.

## Tech stack

### Backend
- Java 25
- Spring MVC 6.2.7 (REST) — Jakarta EE namespace
- Spring Data JPA 3.4.5 / Hibernate
- Jackson (DTO mapping)
- HSQLDB (in-memory, auto-schema)
- Maven

### Web frontend
- AngularJS + Bootstrap

### Android
- Kotlin 2.1 + Jetpack Compose (Material 3)
- Retrofit 2 + Moshi (REST client)
- MVVM with StateFlow
- Min SDK: Android 8.0 (API 26)
- Gradle 8.10

## Build

### Backend

```bash
mvn clean install
```

The build runs unit tests, enforces a minimum 50% JaCoCo code coverage threshold on the domain module, and checks seven ArchUnit architectural rules. CI runs the same checks on every pull request.

### Android

**Prerequisite:** Android SDK — install via [Android Studio](https://developer.android.com/studio) and set `ANDROID_HOME`.

```bash
cd budinv-android
./gradlew assembleDebug   # build APK
./gradlew test            # run unit tests
```

## Running locally

### Backend + web frontend

```bash
mvn install -DskipTests && mvn cargo:run -pl budinv-rest-services
```

This starts an embedded Tomcat 11 on port 8080 with both WARs deployed:

| Application | URL |
|---|---|
| REST API | `http://localhost:8080/budinv-services/rest/` |
| Frontend | `http://localhost:8080/budinv-web/` |

Stop the server with `Ctrl-C`.

### Android app

With the backend running and an emulator started:

```bash
cd budinv-android
adb install app/build/outputs/apk/debug/app-debug.apk
```

The emulator reaches the backend at `http://10.0.2.2:8080/` (Android's alias for host `localhost`). For a physical device, add your machine's IP to `budinv-android/local.properties`:

```properties
BASE_URL=http://<your-machine-ip>:8080/budinv-services/rest/
```

## API

| Method | Path | Description |
|---|---|---|
| GET | `/ledger/hello` | Health check |
| GET | `/ledger/show/list` | Returns the current ledger with entries |

## Status

Early-stage / work in progress. Domain logic (ledger calculations, expense category aggregation, date-range filtering) is implemented and tested. The REST layer currently returns hardcoded data; full persistence integration is still in progress.
