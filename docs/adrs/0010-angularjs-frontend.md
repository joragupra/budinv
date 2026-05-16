# 10. AngularJS 1.0.5 as the frontend framework

Date: 2026-05-16

## Status

Accepted

## Context

The project needs a lightweight frontend to display ledger data. AngularJS was the dominant single-page application framework when the project was started, making it a natural choice at the time.

## Decision

Use AngularJS 1.0.5. The frontend is packaged as a separate WAR (`budinv-web`) that calls the REST API and is independently deployable.

## Consequences

- AngularJS reached end-of-life in December 2021 and no longer receives security patches or bug fixes.
- The framework is sufficient for the current read-only display functionality.
- Any meaningful frontend expansion — new interactions, component reuse, accessibility improvements — should be preceded by a migration to a supported framework (Angular, React, or Vue).
- The separate WAR boundary (see ADR 0008) keeps a future frontend migration self-contained and independent of the backend release.
