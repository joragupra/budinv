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
| `budinv-model` | Domain model and business logic |
| `budinv-controller` | Spring MVC REST controllers and DTOs |
| `budinv-rest-services` | WAR packaging and servlet configuration |
| `budinv-infrastructure` | JPA entities and Spring Data repositories |
| `budinv-web` | AngularJS + Bootstrap frontend |

## Tech stack

- Java 21
- Spring MVC (REST)
- Spring Data JPA / Hibernate
- HSQLDB (in-memory, auto-schema)
- AngularJS + Bootstrap (frontend)
- Maven

## Build

```bash
mvn clean install
```

Deploy the resulting WAR from `budinv-rest-services` to a servlet container (e.g. Tomcat).

## API

| Method | Path | Description |
|---|---|---|
| GET | `/ledger/hello` | Health check |
| GET | `/ledger/show/list` | Returns the current ledger with entries |

## Status

Early-stage / work in progress. The REST layer returns hardcoded data; persistence integration is partially wired.
