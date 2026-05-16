# 5. Plain Spring MVC without Spring Boot

Date: 2026-05-16

## Status

Accepted

## Context

Spring Boot's auto-configuration is convenient but opaque: it pulls in starters, embedded containers, and sensible defaults that can conflict with WAR deployment to an external container and make it harder to reason about what beans are active and why.

## Decision

Use plain Spring MVC 6.x with explicit Java-based `@Configuration` classes (`RestConfig`, `PersistenceConfig`). There is no Spring Boot parent POM and no `@SpringBootApplication`. Every bean — data source, entity manager factory, transaction manager, message converters — is declared explicitly.

## Consequences

- All beans and integration points are visible in source; nothing is auto-configured invisibly.
- WAR deployment to an external servlet container is straightforward with no embedded-container conflicts.
- Spring Boot DevTools, Actuator, and the starter ecosystem are not available.
- More boilerplate configuration is required; any new integration (e.g. security, caching) must be wired manually.
