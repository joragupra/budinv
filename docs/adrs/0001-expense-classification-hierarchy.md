# 1. Three-tier expense classification with dual budget strategies

Date: 2026-05-16

## Status

Accepted

## Context

Household expenses differ in predictability (fixed rent vs. irregular car repair vs. variable groceries) and granularity (a broad category like "Regular" vs. a specific concept like "Rent"). Budgeting must work at both levels: a user wants to know the deviation for a specific concept and the aggregate deviation for its parent category.

## Decision

A two-level taxonomy: `ExpenseConcept` (leaf, e.g. "Rent") rolls up into `ExpenseType` (e.g. "Regular", "Irregular", "Variable"). Two concrete `ExpenseCategory` subclasses implement different budget strategies:

- `DirectlyBudgetedExpenseCategory` — holds an explicit budget amount (used for `ExpenseConcept`).
- `AggregationBudgetedExpenseCategory` — derives its budget by summing the budgets of its children (used for `ExpenseType`).

Both expose the same `getBudgetedAmount()` and `calculateDeviation()` interface via the `Budgetable` marker.

## Consequences

- Budget deviations can be queried consistently at any level of the hierarchy.
- Changing a concept's budget automatically propagates to its parent type through aggregation.
- The inheritance hierarchy is relatively deep; if additional budget strategies emerge, extracting a `BudgetStrategy` interface and using composition would be preferable to further subclassing.
