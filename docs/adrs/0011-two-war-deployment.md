# 11. Two-WAR deployment strategy

Date: 2026-05-16

## Status

Accepted

## Context

Serving the frontend from the same WAR as the REST API couples their release cycles, complicates CORS configuration, and prevents independent scaling. A single artifact also makes it harder to replace one side (e.g. migrate the frontend) without redeploying the other.

## Decision

Package the REST API (`budinv-rest-services`) and the AngularJS frontend (`budinv-web`) as two separate WAR artifacts deployed to the same servlet container. The REST API is mounted at `/rest/*`; the frontend is mounted at `/`.

## Consequences

- Frontend and backend can be deployed and versioned independently.
- The frontend calls the REST API via a known, stable base path (`/rest`).
- Operational complexity increases slightly: two artifacts must be managed and deployed together.
- A reverse proxy in front of both WARs would simplify URL routing and TLS termination in a production environment.
