package com.gildedrose;

// Estrategia de actualizacion de un articulo (patron Strategy).
// Cada tipo de articulo implementa su propia regla de "un dia que pasa".
public interface ItemUpdater {
    void update(Item item);
}
