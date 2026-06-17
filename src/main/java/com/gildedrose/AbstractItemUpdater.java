package com.gildedrose;

// Clase base de las estrategias (patron Template/Strategy). Centraliza las
// operaciones comunes sobre la calidad y el sellIn respetando los limites del
// inventario, evitando duplicar estas reglas en cada updater concreto (DRY).
public abstract class AbstractItemUpdater implements ItemUpdater {

    // Limites de calidad permitidos por las reglas del inventario.
    protected static final int MIN_QUALITY = 0;
    protected static final int MAX_QUALITY = 50;

    protected void decreaseSellIn(Item item) {
        item.setSellIn(item.getSellIn() - 1);
    }

    protected void increaseQuality(Item item) {
        if (item.getQuality() < MAX_QUALITY) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    protected void decreaseQuality(Item item) {
        if (item.getQuality() > MIN_QUALITY) {
            item.setQuality(item.getQuality() - 1);
        }
    }

    protected void resetQuality(Item item) {
        item.setQuality(MIN_QUALITY);
    }

    protected boolean isExpired(Item item) {
        return item.getSellIn() < 0;
    }

    @Override
    public abstract void update(Item item);
}
