# Changelog

Todas las novedades de RickAndMortyCatalog siguen el estándar Semantic Versioning.

## [Unreleased]
- Pendiente de nuevas funcionalidades aprobadas para integrar en `develop`.

## [v1.0.0] - 2025-11-05
### Añadido
- Listado principal de personajes en Jetpack Compose con soporte de Paging 3 y carga remota desde la API oficial.
- Pantalla de detalle con navegación Compose, vista previa y caso de uso dedicado para recuperar personajes.
- Repositorios singleton con Retrofit + OkHttp, capa de dominio con casos de uso y estructura modular en `data`, `domain`, `di` y `ui`.

### Corregido
- Serialización explícita de `CharacterUI` para la navegación y codificación/decodificación de argumentos de detalle.
- Resolución de referencias de versión para el plugin de Kotlin Serialization y configuración de dependencias clave.

### Pruebas
- Suites de unit tests para viewmodels, repositorios y `CharacterPagingSource` usando JUnit, MockK y Turbine.
- Configuración base de instrumentation tests con el runner de AndroidX para futuras regresiones UI.
