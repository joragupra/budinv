# 7. JaCoCo coverage thresholds enforced on domain module only

Date: 2026-05-16

## Status

Accepted

## Context

Enforcing a coverage gate across all modules is problematic: `budinv-controller` and `budinv-infrastructure` require a Spring context or integration setup to test meaningfully, making high unit-test coverage there an unreliable proxy for quality. The domain module is pure Java, has no infrastructure dependencies, and is fully testable in isolation.

## Decision

JaCoCo is configured in `budinv-model` only, with minimum thresholds of 50% line coverage and 40% branch coverage enforced at `verify` time. Other modules have JaCoCo reporting enabled but no enforced gate.

## Consequences

- The build fails if domain logic regresses in coverage, protecting the core business rules.
- Coverage for the controller and infrastructure layers is measured and visible in reports but is not a build gate.
- The thresholds are deliberately conservative; they should be raised incrementally as the test suite matures.
- The absence of integration test coverage enforcement is an accepted gap until integration tests are introduced.
