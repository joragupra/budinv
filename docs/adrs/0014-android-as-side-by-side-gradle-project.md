# 14. Android app as a side-by-side Gradle project

Date: 2026-05-30

## Status

Accepted

## Context

Adding a native Android client to the monorepo requires choosing how to integrate it with the existing Maven build. Android's official and only supported build system is Gradle — the Android Maven Plugin (android-maven-plugin) is archived and unsupported for modern Android development (AGP 4+, Kotlin, Jetpack Compose). Two integration strategies were considered:

1. **Side-by-side Gradle project** — `budinv-android/` lives as a sibling directory with its own Gradle build. It is not listed as a module in the parent `pom.xml`. CI adds a second job.
2. **Maven wrapper around Gradle** — `budinv-android` is added as a Maven module whose `pom.xml` uses the `exec-maven-plugin` to shell out to `./gradlew`. This preserves `mvn clean install` as a single build command.

## Decision

Use a side-by-side Gradle project. `budinv-android/` is a standalone Gradle project alongside the five Maven modules. The parent `pom.xml` is unchanged. CI runs a separate `android` job.

The Maven wrapper approach was rejected because its only benefit — a single build command — disappears in practice: Android Studio (the tool developers use daily) talks to Gradle directly and ignores the `pom.xml` entirely. The wrapper adds a layer of indirection that makes build failures harder to diagnose without providing any real integration.

## Consequences

- Android is developed with the full, supported toolchain: Android Studio, `./gradlew`, all AGP features work as documented.
- `mvn clean install` builds only the backend; `./gradlew assembleDebug` builds only the Android app. Developers need to know both entry points.
- CI has two independent jobs (`test` for Maven, `android` for Gradle). A failure in one does not block the other.
- The Android SDK must be installed separately from the JDK used by the Maven build. Local setup requires both.
