# 6. Hand-written DTO transformer over mapping frameworks

Date: 2026-05-16

## Status

Accepted

## Context

Mapping frameworks such as MapStruct or ModelMapper reduce boilerplate at the cost of a build-time annotation processor (MapStruct) or runtime reflection (ModelMapper). Generated or reflective mapping can also obscure what is being mapped and make debugging non-obvious failures harder.

## Decision

`LedgerTransformer` is a hand-written class that maps between domain objects and DTOs explicitly. The DTO package structure mirrors the domain package structure to keep the correspondence clear. No annotation processor is added to the build.

## Consequences

- Mapping logic is plain Java, fully visible, and straightforward to debug.
- Adding or renaming a field requires a manual update to the transformer; the compiler will not catch missing mappings automatically.
- The approach is practical while the domain model is small. If the model grows significantly in breadth, the maintenance cost of hand-written mappings should be re-evaluated against a code-generation approach like MapStruct.
