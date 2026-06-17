package com.gildedrose;

// Aged Brie: gana calidad con el paso del tiempo y el doble una vez vencido.
public class AgedBrieUpdater extends AbstractItemUpdater {

    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        increaseQuality(item);
        if (isExpired(item)) {
            increaseQuality(item);
        }
    }
}
