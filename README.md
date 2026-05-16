# budinv

A personal budget and expense tracking web application.

## What it does

- Maintain a **Ledger** for a date range, containing income and expense entries
- Organize expenses into a two-level hierarchy: **ExpenseType** → **ExpenseConcept** (e.g. *Transport* → *Car maintenance*)
- Define a **Budget** with planned amounts for three expense kinds:
  - **Regular** — known, recurring expenses (rent, subscriptions)
  - **Irregular** — known but infrequent (insurance renewals, taxes)
  - **Variable** — day-to-day discretionary spending
- Calculate income, total spending, balance, and budget deviation for any period

## Architecture

Maven multi-module project:

| Module | Contents |
|---|---|
| `budinv-model` | Domain model (`domain/` package) and business logic |
| `budinv-controller` | Spring MVC REST controllers, DTOs, and Java-based `@Configuration` classes |
| `budinv-rest-services` | WAR packaging and servlet configuration |
| `budinv-infrastructure` | JPA entities, Spring Data repositories, and Java-based persistence configuration |
| `budinv-web` | AngularJS + Bootstrap frontend |

Spring configuration is Java-based (`RestConfig`, `PersistenceConfig`) — there are no XML application context files.

## Tech stack

- Java 25
- Spring MVC 6.2.7 (REST) — Jakarta EE namespace
- Spring Data JPA 3.4.5 / Hibernate
- Jackson (DTO mapping)
- HSQLDB (in-memory, auto-schema)
- AngularJS + Bootstrap (frontend)
- Maven

## Build

```bash
mvn clean install
```

The build runs unit tests and enforces a minimum 50% JaCoCo code coverage threshold. CI runs the same check on every pull request.

## Running locally

```bash
mvn install -DskipTests && mvn cargo:run -pl budinv-rest-services
```

This starts an embedded Tomcat 11 on port 8080 with both WARs deployed:

| Application | URL |
|---|---|
| REST API | `http://localhost:8080/budinv-services/rest/` |
| Frontend | `http://localhost:8080/budinv-web/` |

Stop the server with `Ctrl-C`.

## API

| Method | Path | Description |
|---|---|---|
| GET | `/ledger/hello` | Health check |
| GET | `/ledger/show/list` | Returns the current ledger with entries |

## Status

Early-stage / work in progress. Domain logic (ledger calculations, expense category aggregation, date-range filtering) is implemented and tested. The REST layer currently returns hardcoded data; full persistence integration is still in progress.
