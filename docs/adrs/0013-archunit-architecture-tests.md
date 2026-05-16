# 13. ArchUnit for enforcing architectural rules

Date: 2026-05-16

## Status

Accepted

## Context

The five-module Maven structure and the package conventions that separate domain, DTO, entity, and repository classes are enforced only by convention. Nothing in the build prevents a developer from adding a Spring annotation to a domain class or accessing a JPA entity directly from a controller. Violations of these boundaries would be discovered late, if at all.

## Decision

Introduce [ArchUnit](https://www.archunit.org) (`archunit-junit5` 1.3.0) as a test dependency in `budinv-controller`. That module has the widest classpath visibility — it depends on both `budinv-model` and `budinv-infrastructure` — making it the natural host for cross-module architectural rules. Tests are written as plain JUnit 5 `@Test` methods using a `ClassFileImporter` that scans the compiled output of the three Java modules at test time.

Seven rules are enforced:

1. Domain classes (`..domain..`) must not import Spring, JPA, Jackson, or JAXB.
2. Domain classes must not depend on DTO classes.
3. Domain classes must not depend on JPA entity classes.
4. `@RestController` classes must reside in the root package `com.joragupra.budinv`.
5. `@Entity` classes must reside in `..entity..`.
6. Spring Data `Repository` interfaces must reside in `..repository..`.
7. `@RestController` classes must not directly use JPA entity classes.

As a direct consequence of introducing ArchUnit 1.3.0, the project's Java compile target was reduced from 25 to 21. ArchUnit's bundled ASM library does not yet support Java 25 class files (major version 69), and the codebase uses no Java 25-specific language features, making Java 21 (the current LTS) a safe target.

## Consequences

- Architectural boundary violations are caught at build time, not in code review.
- ArchUnit tests do not contribute to JaCoCo coverage metrics, because they analyse bytecode statically without executing production code.
- The Java compile target is 21, not 25. This should be revisited when a version of ArchUnit compatible with Java 25 class files is available.
- New architectural conventions must be accompanied by a corresponding ArchUnit rule to be considered enforced.
