
# Rick-Morty

RickAndMortyCatalog es una app de práctica para profundizar en Android con soporte de IA. Usa arquitectura MVVM, patrón repositorio y carga paginada para explorar personajes de la serie.

## Tech Stack
- Jetpack Compose for UI with Navigation and Material 3
- Hilt for dependency injection
- Retrofit + OkHttp for network calls to rickandmortyapi.com
- Paging 3 for incremental data loading

## Build & Run
- Android Studio: abre el proyecto y ejecuta en un emulador o dispositivo.
- Gradle CLI: `./gradlew assembleDebug` genera el APK de debug.
- Tests: `./gradlew testDebugUnitTest` para unit tests, `./gradlew connectedAndroidTest` para instrumentation tests.

## Flujo de Trabajo
- Consulta las [Repository Guidelines](AGENTS.md) para estructura, comandos y estilo.
- Crea nuevas funcionalidades desde `develop` usando ramas `feature/<nombre>`. No merges a `develop` sin aprobación.
- Cada merge en `develop` debe incrementar `versionCode`/`versionName`, actualizar `CHANGELOG.md` y etiquetarse con la versión (`vX.Y.Z`).
=======
# Rick and Morty Catalog

This repository contains a simple Android application built with **Jetpack Compose**. The project demonstrates a minimal setup using the Kotlin DSL for Gradle and version catalogs.

## Project structure

```
.
├── app
│   ├── build.gradle.kts          # Module build script
│   ├── proguard-rules.pro        # ProGuard configuration
│   └── src
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/lnavarro/rickmorty
│       │   │   ├── MainActivity.kt
│       │   │   └── ui/theme/
│       │   │       ├── Color.kt
│       │   │       ├── Theme.kt
│       │   │       └── Type.kt
│       ├── androidTest           # Instrumented tests
│       └── test                  # Unit tests
├── build.gradle.kts              # Top level build script
├── gradle
│   ├── libs.versions.toml        # Dependency version catalog
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── settings.gradle.kts
```

The project currently consists of a single module (`app`). UI is implemented using Jetpack Compose with Material3 components. The Gradle configuration uses a version catalog (`gradle/libs.versions.toml`) to keep dependencies tidy.

## Build and run

Use the included Gradle wrapper to build or run the project:

```bash
./gradlew assembleDebug   # Build the debug APK
./gradlew installDebug    # Install on a connected device
```

You can open the project with Android Studio Hedgehog or newer for the best Compose experience.

## About the architecture

This codebase is intentionally lightweight and follows the default Android Studio Compose template. The main composables live inside `MainActivity.kt`, and theming is isolated in the `ui/theme` package. As the project grows, you can separate features into packages or modules following common Android architecture patterns (e.g., MVVM, Clean Architecture). For now, it simply showcases the basic setup required to start building a Compose application.
