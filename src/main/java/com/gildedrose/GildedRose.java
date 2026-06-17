package com.gildedrose;

// Orquesta la actualizacion diaria del inventario. Ya no conoce las reglas de
// cada articulo: delega en la estrategia (ItemUpdater) que le entrega la fabrica,
// por lo que su logica permanece estable aunque se agreguen nuevos tipos.
class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater updater = ItemUpdaterFactory.getUpdater(item);
            updater.update(item);
        }
    }
}
