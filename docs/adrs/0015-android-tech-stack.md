# 15. Android tech stack: Kotlin, Jetpack Compose, Retrofit, Moshi

Date: 2026-05-30

## Status

Accepted

## Context

The Android client needs to fetch and display ledger data from the REST API (`GET /ledger/show/list`). The response contains a polymorphic list of entries discriminated by an `entryType` field (`"INCOME"` / `"EXPENSE"`). The app is a read-only viewer at this stage with a single screen.

## Decision

- **Language:** Kotlin. The modern standard for Android; full coroutines support for async API calls.
- **UI:** Jetpack Compose with Material 3. Declarative UI that integrates naturally with Kotlin and StateFlow; no XML layouts.
- **HTTP client:** Retrofit 2 + OkHttp. The de-facto standard for REST on Android.
- **JSON:** Moshi with `PolymorphicJsonAdapterFactory`. Moshi's `PolymorphicJsonAdapterFactory` handles the `entryType` discriminator cleanly, mapping `"INCOME"` and `"EXPENSE"` to distinct Kotlin sealed subclasses. The server's sealed Java DTO hierarchy (`Income`, `IncurredExpense`) is mirrored as a Kotlin sealed class on the client.
- **Architecture:** MVVM with `StateFlow`. `LedgerViewModel` exposes a single `uiState: StateFlow<LedgerUiState>` with `Loading`, `Success`, and `Error` states. No dependency injection framework — the app is too small to justify the overhead.
- **Min SDK:** API 26 (Android 8.0), covering approximately 95% of active devices.

## Consequences

- The Kotlin sealed class hierarchy on the client mirrors the server's sealed Java hierarchy, keeping the type model consistent across both codebases.
- `PolymorphicJsonAdapterFactory` requires the discriminator field (`entryType`) to be present in every entry JSON object; the server already guarantees this.
- Without a DI framework, `RetrofitClient` is a Kotlin `object` (singleton). If the app grows to multiple screens or requires different configurations per environment, introducing Hilt would be the natural next step.
- Amounts are `Double` on both server and client. This is sufficient for display but would require `BigDecimal` if arithmetic were ever performed on the client side.
