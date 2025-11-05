# Repository Guidelines

## Project Structure & Module Organization
The single Android module lives in `app/`. Production code under `src/main/java/com/lnavarro/rickmorty` splits into `data` (Retrofit client, paging source, DTOs, repository singletons per core feature), `domain` (use cases, domain models), `ui` (Compose screens, navigation, components, theme), and `di` (Hilt modules). Resources stay in `src/main/res`. JVM tests mirror the packages in `src/test/java`, while instrumentation specs sit in `src/androidTest/java`. Generated build outputs land in `app/build` and should remain untracked.

## Build, Test, and Development Commands
Use `./gradlew assembleDebug` for a local debug APK. Run JVM unit tests with `./gradlew testDebugUnitTest`. Execute instrumentation tests on an attached device or emulator with `./gradlew connectedAndroidTest`. `./gradlew lintDebug` catches lint and Compose-specific warnings; keep it clean before pushing. When dependencies change, a full sync via `./gradlew --refresh-dependencies` ensures the IDE stays aligned.

## Coding Style & Naming Conventions
All code is Kotlin with 4-space indentation and idiomatic Kotlin expressions. Favor immutable `data` classes for models and keep Compose functions side-effect free. Name Composables with PascalCase (e.g., `CharacterCard`), suffix view models with `ViewModel`, repositories with `Repository`, and use case classes with `UseCase`. Keep DI wiring in `di/NetworkModule.kt` and related files; prefer constructor injection. Run Android Studio’s Kotlin formatter before committing.

## Testing Guidelines
Add unit tests alongside features in `src/test/java`, mirroring the package path. Leverage JUnit4, MockK, Turbine, and the Paging testing utilities already configured. Name tests to express behavior (e.g., `shouldLoadNextPageWhenScrolledToEnd`). Always run `./gradlew testDebugUnitTest` locally, and trigger `./gradlew connectedAndroidTest` whenever UI or navigation flows change. Attach emulator or device results in the PR if instrumentation tests are run.

## Commit & Pull Request Guidelines
Work from the shared `develop` branch; branch off as `feature/<descripcion-corta>` and keep commits scope-limited with Conventional Commit headers (`feat:`, `fix:`, etc.). Do not merge back into `develop` without explicit approval. Pull requests should include a brief summary, validation notes (commands or devices exercised), and links to issues or context. Provide screenshots or Compose preview captures when UI changes are visible. Address review feedback with follow-up commits instead of force pushes.

## Release & Versioning Workflow
When a change lands in `develop`, tag the merge commit with the next semantic version (`v1.1.0`, etc.), bump `versionCode`/`versionName` in `app/build.gradle.kts`, and append an entry to `CHANGELOG.md` describing the additions. Coordinate tagging with the product owner to keep the training iterations aligned.

## Architecture Notes
The app layers data, domain, and UI: repositories orchestrate Retrofit + OkHttp calls, domain use cases expose suspending APIs, and view models publish `StateFlow` consumed by Compose screens. Hilt modules in `di/` register network and repository bindings; remember to add new bindings there. Navigation is driven by Compose Navigation in `ui/navigation/Navigation.kt`, so keep route strings centralized and encode arguments consistently with the existing detail screen pattern. Treat the project as a learning sandbox—favor clear separation of concerns and document decisions in the changelog for future iterations.
