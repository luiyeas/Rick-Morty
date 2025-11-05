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
