package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

// Selecciona la estrategia adecuada segun el nombre del articulo. Concentrar
// aqui el mapeo permite agregar nuevos tipos sin tocar GildedRose (principio
// abierto/cerrado): basta con registrar un updater mas en el mapa.
public final class ItemUpdaterFactory {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Mana Cake";

    private static final Map<String, ItemUpdater> UPDATERS = new HashMap<>();
    private static final ItemUpdater DEFAULT_UPDATER = new DefaultItemUpdater();

    static {
        UPDATERS.put(AGED_BRIE, new AgedBrieUpdater());
        UPDATERS.put(BACKSTAGE_PASS, new BackstagePassUpdater());
        UPDATERS.put(SULFURAS, new SulfurasUpdater());
        UPDATERS.put(CONJURED, new ConjuredItemUpdater());
    }

    private ItemUpdaterFactory() {
    }

    public static ItemUpdater getUpdater(Item item) {
        return UPDATERS.getOrDefault(item.getName(), DEFAULT_UPDATER);
    }
}
