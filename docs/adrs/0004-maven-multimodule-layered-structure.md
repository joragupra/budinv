# 4. Maven multi-module layered structure

Date: 2026-05-16

## Status

Accepted

## Context

A budgeting tool needs clean separation between pure business logic, HTTP concerns, persistence, and UI. Without enforced module boundaries, cross-cutting dependencies creep in silently — domain classes start importing Jackson, controllers import JPA entities, and the model becomes untestable without a full Spring context.

## Decision

Split the project into five Maven modules with a strict dependency direction:

- `budinv-model` — pure domain (no annotations, no framework dependencies)
- `budinv-controller` — REST endpoints, DTOs, Spring MVC configuration
- `budinv-infrastructure` — JPA entities and Spring Data repositories
- `budinv-rest-services` — WAR assembly (pulls together controller + infrastructure)
- `budinv-web` — AngularJS frontend WAR

Maven dependency declarations make illegal dependencies (e.g. `budinv-model` importing Spring) a compile error.

## Consequences

- Dependency direction is enforced at build time, not by convention.
- Each module can be built, tested, and reasoned about in isolation.
- `budinv-rest-services` is intentionally a thin assembly module with no logic of its own.
- Five separate `pom.xml` files add coordination overhead for dependency management and version alignment.
