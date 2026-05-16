# 12. Jakarta EE 10 namespace (`jakarta.*`)

Date: 2026-05-16

## Status

Accepted

## Context

Jakarta EE 9 renamed all `javax.*` packages to `jakarta.*`. Spring 6.x requires Jakarta EE 9+ and is incompatible with the legacy `javax.*` namespace. Java 25 does not bundle `javax.*` APIs. Using `javax.*` would block adoption of Spring 6.x and constrain the Java version.

## Decision

Use `jakarta.*` imports throughout the codebase (`jakarta.persistence`, `jakarta.xml.bind`, `jakarta.servlet`). Spring 6.2.x and Hibernate 6.x are selected specifically because they align with the `jakarta.*` namespace.

## Consequences

- The stack is compatible with Java 25 and Spring 6.x without namespace shims.
- Any third-party library that still imports `javax.*` is incompatible and must be updated, replaced, or wrapped before it can be used.
- This is a one-way migration: reverting to `javax.*` is not practical once the full stack is on `jakarta.*`.
