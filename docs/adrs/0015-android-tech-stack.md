# 15. Android tech stack: Kotlin, Jetpack Compose, bundled domain model

Date: 2026-05-30

## Status

Accepted

## Context

The initial Android client needs to add and display ledger entries. Rather than connect to the backend REST API straight away, the first iteration bundles the `budinv-model` JAR directly and runs an in-memory `Ledger` on-device. This avoids network complexity while the Android module is being established, and lets the domain model be reused without duplication.

## Decision

- **Language:** Kotlin. The modern standard for Android; full coroutines support for async operations.
- **UI:** Jetpack Compose with Material 3. Declarative UI that integrates naturally with Kotlin and StateFlow; no XML layouts.
- **Domain model:** `budinv-model` is bundled as a local JAR (`app/libs/budinv-model.jar`). The Android app uses the same domain classes (`Ledger`, `Income`, `IncurredExpense`, etc.) as the backend, with no duplication. To update the domain, rebuild `budinv-model` with Maven and copy the resulting JAR.
- **Data layer:** `InMemoryLedgerRepository` holds a single `Ledger` instance initialised to the current calendar month. Data is not persisted — all entries are lost on process death. This is an intentional scope constraint for the initial release; network/persistence integration is a future step.
- **Architecture:** MVVM with `StateFlow`. `LedgerViewModel` exposes a single `uiState: StateFlow<LedgerUiState>` with `Loading`, `Success`, and `Error` states. No dependency injection framework — the app is too small to justify the overhead.
- **Min SDK:** API 26 (Android 8.0), covering approximately 95% of active devices.

## Consequences

- The app runs fully standalone — no backend required.
- Data is in-memory only; entries do not survive a process restart. Users should be aware of this limitation.
- The domain model is shared by value (a copied JAR), not by reference. Keeping the JAR in sync with `budinv-model` is a manual step until a proper build integration is added.
- When network integration is introduced, a new ADR should document the HTTP client and JSON strategy choices (candidates: Retrofit + Moshi, Ktor, plain OkHttp).
