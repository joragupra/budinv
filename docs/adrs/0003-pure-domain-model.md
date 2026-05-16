# 3. Pure domain model decoupled from persistence and serialisation

Date: 2026-05-16

## Status

Accepted

## Context

Mixing JPA (`@Entity`, `@Column`) or Jackson (`@JsonProperty`) annotations into domain classes couples business logic to infrastructure concerns. This makes domain objects slower to instantiate, harder to unit-test, and entangled with framework lifecycle management.

## Decision

`budinv-model` carries zero infrastructure dependencies. JPA entities live exclusively in `budinv-infrastructure` and replicate only the data that needs to be persisted. Serialisation annotations live only in the DTO classes inside `budinv-controller`. The stub `entity/Ledger.java` is a deliberate placeholder acknowledging the domain-to-persistence translation boundary.

## Consequences

- Domain classes are plain Java — instantiated with `new`, testable without a Spring or JPA context, and free to evolve without annotation constraints.
- A translation layer between domain objects and JPA entities is required; it is currently stubbed and must be completed when durable persistence is introduced.
- The discipline must be actively maintained: adding a framework dependency to `budinv-model` will break the build, enforcing the boundary automatically.
