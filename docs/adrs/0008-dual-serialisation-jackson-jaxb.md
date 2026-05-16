# 8. Dual serialisation support: Jackson (JSON) and JAXB (XML)

Date: 2026-05-16

## Status

Accepted

## Context

REST APIs may need to serve both JSON and XML clients. Spring MVC's content negotiation selects the appropriate serialiser based on the `Accept` header. Supporting XML with JAXB requires minimal additional code when DTOs are annotated with `@XmlRootElement`.

## Decision

DTOs carry `@XmlRootElement` (JAXB) annotations alongside their plain Java structure. Spring's `MappingJackson2HttpMessageConverter` handles `application/json` (the default). A JAXB-backed converter handles `application/xml` when explicitly requested by a client.

## Consequences

- Both JSON and XML are supported from a single DTO model with no duplication of response logic.
- JAXB annotations (`@XmlRootElement`, `@XmlElement`) add noise to DTO classes.
- If XML is never consumed in practice, these annotations should be removed to simplify the model.
