# Proyecto 1: Gilded Rose Refactoring Kata

## Introducción

Este proyecto se desarrolla como parte del curso **PSWE-03 Construcción y Mantenimiento de Software**. El trabajo utiliza la kata [Gilded Rose](https://github.com/emilybache/GildedRose-Refactoring-Kata) como base para analizar y mejorar código legado.

El objetivo es identificar problemas de diseño y refactorizar la solución aplicando principios como DRY, SRP, KISS, modularidad, separación de responsabilidades, bajo acoplamiento, alta cohesión y SOLID. Las pruebas automatizadas permiten documentar las reglas de negocio y comprobar que el comportamiento se conserve durante la refactorización.

## Problemas identificados

Los siguientes problemas se identificaron en `GildedRose.java`:

| Clasificación | Problema | Impacto |
|---|---|---|
| Responsabilidad única | `updateQuality()` concentra la identificación del artículo, la actualización de `sellIn` y todas las reglas de calidad | Dificulta comprender, probar y modificar cada comportamiento por separado |
| Abierto para extensión, cerrado para modificación | Cada nuevo tipo de artículo requiere agregar condiciones dentro de `updateQuality()` | Aumenta el riesgo de afectar reglas existentes |
| Inversión de dependencias | `GildedRose` depende directamente de nombres y comportamientos concretos | Produce alto acoplamiento y dificulta sustituir o extender reglas |
| Mantenerlo simple | El método contiene lógica negada, condiciones anidadas y un orden de operaciones poco evidente | Incrementa la complejidad y facilita introducir errores |
| No repetirse | Se repiten comparaciones de nombres, validaciones de límites y cambios de calidad | Una regla debe modificarse en varios lugares y puede quedar inconsistente |
| Claridad | Se utilizan cadenas y números sin nombres, como `50`, `11`, `6` y los nombres especiales | El significado de las reglas no es evidente y los errores tipográficos pueden cambiar el comportamiento |
| Encapsulamiento | Los campos del artículo y el inventario son mutables y accesibles directamente | El estado puede modificarse sin respetar las reglas del negocio |
| Robustez | No se validan valores nulos ni estados iniciales inválidos | El sistema puede fallar o procesar datos incorrectos |
| Documentación | Las reglas de negocio no están expresadas mediante métodos, nombres o comentarios claros | El mantenimiento depende de interpretar un bloque complejo de condiciones |

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
