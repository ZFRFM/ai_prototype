# AGENTS.md

## Project

This is a small Android app built with Gradle Kotlin DSL, Kotlin, and Jetpack
Compose. Keep changes focused and easy to review.

## Repository Layout

- App module: `app`
- Main Kotlin sources: `app/src/main/java`
- Unit tests: `app/src/test/java`
- Instrumented tests: `app/src/androidTest/java`
- Shared dependency versions: `gradle/libs.versions.toml`

## Commands

Run these from the repository root:

- List tasks: `./gradlew tasks --all`
- Unit tests: `./gradlew :app:testDebugUnitTest`
- Lint: `./gradlew :app:lintDebug`
- Debug build: `./gradlew :app:assembleDebug`

Before opening a pull request, run:

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

## Android Rules

- Prefer Compose idioms already used in the project.
- Keep UI state and UI rendering separated when behavior grows.
- Add or update unit tests when state mapping or business behavior changes.
- Add or update instrumented/Compose UI tests when interaction behavior changes.
- Do not require manual Android Studio actions for build or test verification.

## Review Rules

- Do not change unrelated files.
- Do not commit secrets or machine-local paths.
- Do not edit `local.properties` unless the task explicitly requires local SDK setup.
- Include a short verification summary in the final response or PR description.
- If a Gradle command fails because of environment setup rather than the change,
  report the exact failure and the command that produced it.
