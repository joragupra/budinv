# 2. Sealed `BookkeepingEntry` hierarchy with pattern matching

Date: 2026-05-16

## Status

Accepted

## Context

A ledger entry is either income or an incurred expense — never an arbitrary extension. Open class hierarchies allow unknown subclasses, which forces defensive `instanceof` chains or `default` branches in switches that silently ignore new types introduced later.

## Decision

`BookkeepingEntry` is a sealed abstract class permitting exactly two subclasses: `Income` and `IncurredExpense`. All code that needs to distinguish entry types uses exhaustive `switch` expressions. The same sealed pattern is replicated in the DTO hierarchy to preserve the guarantee across the serialisation boundary.

## Consequences

- Exhaustive `switch` expressions over entries are verified at compile time; forgetting to handle a case is a build error.
- Adding a new entry type is a deliberate, high-friction change: every `switch` site must be updated.
- Requires Java 17+ (sealed classes are a standard feature from Java 17).
- Replicating the sealed hierarchy on the DTO side adds some duplication but ensures the type safety guarantee is not lost when crossing into the HTTP layer.
