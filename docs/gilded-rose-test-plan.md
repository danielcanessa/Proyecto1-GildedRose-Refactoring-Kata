# Plan de pruebas unitarias: Gilded Rose

## Objetivo

Validar el comportamiento de `GildedRose.updateQuality()` antes de refactorizarlo, de modo que los cambios de diseño no alteren las reglas del negocio.

Las pruebas comprobarán el estado de los artículos después de procesar **un día**:

- Cambio de `sellIn`.
- Cambio de `quality`.
- Límites de calidad entre `0` y `50`.
- Reglas especiales de cada tipo de artículo.
- Conservación del nombre del artículo.

## Alcance

El plan cubre:

- Artículos normales.
- `Aged Brie`.
- `Backstage passes to a TAFKAL80ETC concert`.
- `Sulfuras, Hand of Ragnaros`.
- Inventarios vacíos.

## Convenciones

Cada entrada se representa como:

```text
Item(nombre, sellIn, quality)
```

Cada salida corresponde al estado esperado después de ejecutar una vez:

```java
gildedRose.updateQuality();
```

Se utilizarán los nombres exactos de los artículos especiales, ya que la implementación actual los identifica mediante cadenas de texto.

`Sulfuras` se considera una excepción al límite general de calidad: comienza con calidad `80` y conserva ese valor.

## Estrategia

Se aplicarán particiones de equivalencia y análisis de valores límite. Los casos similares se implementarán como pruebas parametrizadas para evitar métodos repetitivos.

Cada escenario debe verificar `name`, `sellIn` y `quality`. Puede utilizarse un método auxiliar de aserción para no repetir las mismas verificaciones.

## Casos de prueba

### UT-01: Degradación de artículos normales

**Método propuesto:** `normalItemDegradesAccordingToExpiration`

**Objetivo:** comprobar la degradación normal, la degradación doble al vencer y el límite mínimo de calidad.

| Caso | Entrada | Salida esperada | Comportamiento cubierto |
|---|---|---|---|
| 1 | `Item("Normal Item", 5, 10)` | `Item("Normal Item", 4, 9)` | Antes de vencer pierde 1 punto de calidad |
| 2 | `Item("Normal Item", 0, 10)` | `Item("Normal Item", -1, 8)` | Al vencer pierde 2 puntos de calidad |
| 3 | `Item("Normal Item", 0, 1)` | `Item("Normal Item", -1, 0)` | La degradación doble no produce calidad negativa |

### UT-02: Maduración de Aged Brie

**Método propuesto:** `agedBrieImprovesAccordingToExpiration`

**Objetivo:** comprobar que `Aged Brie` aumenta su calidad a velocidad normal antes de vencer y al doble después de vencer.

| Caso | Entrada | Salida esperada | Comportamiento cubierto |
|---|---|---|---|
| 1 | `Item("Aged Brie", 5, 10)` | `Item("Aged Brie", 4, 11)` | Antes de vencer gana 1 punto de calidad |
| 2 | `Item("Aged Brie", 0, 10)` | `Item("Aged Brie", -1, 12)` | Al vencer gana 2 puntos de calidad |

### UT-03: Incrementos y expiración de Backstage passes

**Método propuesto:** `backstagePassChangesAtConcertThresholds`

**Objetivo:** comprobar los límites exactos de cada rango de incremento y la pérdida total de valor después del concierto.

| Caso | Entrada | Salida esperada | Comportamiento cubierto |
|---|---|---|---|
| 1 | `Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)` | `Item("Backstage passes to a TAFKAL80ETC concert", 10, 21)` | Con más de 10 días gana 1 punto |
| 2 | `Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)` | `Item("Backstage passes to a TAFKAL80ETC concert", 9, 22)` | Con 10 días gana 2 puntos |
| 3 | `Item("Backstage passes to a TAFKAL80ETC concert", 6, 20)` | `Item("Backstage passes to a TAFKAL80ETC concert", 5, 22)` | Con 6 días todavía gana 2 puntos |
| 4 | `Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)` | `Item("Backstage passes to a TAFKAL80ETC concert", 4, 23)` | Con 5 días gana 3 puntos |
| 5 | `Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)` | `Item("Backstage passes to a TAFKAL80ETC concert", -1, 0)` | Después del concierto pierde toda su calidad |

### UT-04: Límite máximo de calidad

**Método propuesto:** `improvingItemsNeverExceedMaximumQuality`

**Objetivo:** comprobar que los artículos cuya calidad aumenta nunca superan el máximo de `50`, incluso cuando su regla intenta sumar más de un punto.

| Caso | Entrada | Salida esperada | Comportamiento cubierto |
|---|---|---|---|
| 1 | `Item("Aged Brie", 0, 49)` | `Item("Aged Brie", -1, 50)` | El incremento doble se detiene en 50 |
| 2 | `Item("Backstage passes to a TAFKAL80ETC concert", 5, 49)` | `Item("Backstage passes to a TAFKAL80ETC concert", 4, 50)` | El incremento triple se detiene en 50 |

### UT-05: Inmutabilidad de Sulfuras

**Método propuesto:** `sulfurasNeverChanges`

**Objetivo:** comprobar que el artículo legendario no modifica su fecha de venta ni su calidad.

| Entrada | Salida esperada |
|---|---|
| `Item("Sulfuras, Hand of Ragnaros", 0, 80)` | `Item("Sulfuras, Hand of Ragnaros", 0, 80)` |

### UT-06: Inventario vacío

**Método propuesto:** `emptyInventoryCanBeUpdated`

**Objetivo:** comprobar que actualizar un inventario vacío no produce excepciones ni agrega artículos.

| Entrada | Salida esperada |
|---|---|
| `Item[] {}` | Arreglo vacío, sin excepciones |

## Criterios de aceptación

El conjunto de pruebas se considera satisfactorio cuando:

1. Todos los casos se ejecutan automáticamente con JUnit 5.
2. Cada prueba usa datos nuevos y no depende del orden de ejecución.
3. Las pruebas verifican el estado después de una sola llamada a `updateQuality()`.
4. Los casos parametrizados generan resultados independientes y fáciles de identificar.
5. Todas las pruebas pasan antes y después de la refactorización.
