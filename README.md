# Proyecto 1: Gilded Rose Refactoring Kata

## Introducción

Este proyecto se desarrolla como parte del curso **PSWE-03 Construcción y Mantenimiento de Software**. El trabajo utiliza la kata [Gilded Rose](https://github.com/emilybache/GildedRose-Refactoring-Kata) como base para analizar y mejorar código legado.

El objetivo es identificar problemas de diseño y refactorizar la solución aplicando principios como DRY, SRP, KISS, modularidad, separación de responsabilidades, bajo acoplamiento, alta cohesión y SOLID. Las pruebas automatizadas permiten documentar las reglas de negocio y comprobar que el comportamiento se conserve durante la refactorización.

## Historial de cambios

| Commit | ¿Por qué este cambio? |
|---|---|
| `05fca6e` - Agregar pruebas unitarias para Gilded Rose | Para refactorizar código se necesita un arnés de pruebas sólido que permita detectar y evitar regresiones. |

## Ejecución de pruebas

### Requisitos

- JDK 21 configurado como Java activo del entorno.
- No es necesario instalar Gradle; el proyecto incluye Gradle Wrapper.

### Ejecutar las pruebas unitarias

En macOS o Linux:

```bash
./gradlew test
```

En Windows:

```powershell
.\gradlew.bat test
```

Una ejecución correcta finaliza con:

```text
BUILD SUCCESSFUL
```

Para forzar nuevamente la ejecución de todas las pruebas:

```bash
./gradlew test --rerun-tasks
```

El reporte HTML se genera en:

```text
build/reports/tests/test/index.html
```
