# 9. In-memory HSQLDB with Hibernate auto-DDL and no schema migration tooling

Date: 2026-05-16

## Status

Accepted

## Context

Early development focuses on the domain model and API shape. Introducing a durable database and migration scripts (Flyway, Liquibase) before the schema stabilises creates friction: every domain model change requires a new migration, and the overhead slows iteration without providing real value yet.

## Decision

Use HSQLDB in-memory (`jdbc:hsqldb:mem:budinv`) with Hibernate `hbm2ddl.auto` generating the schema at startup. No Flyway or Liquibase. All data is volatile and lost on restart.

## Consequences

- Zero external setup to run the application locally; the database is created fresh each time.
- The schema always matches the JPA entity definitions exactly.
- All data is lost on every restart — the application cannot be used in production in this configuration.
- This decision must be revisited when durable persistence is required: a real database, a migration tool, and a translation layer between domain objects and JPA entities will all be needed at that point.
