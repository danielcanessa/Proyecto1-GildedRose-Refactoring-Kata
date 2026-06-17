package com.gildedrose;

// Articulo normal: pierde una unidad de calidad por dia y el doble una vez vencido.
public class DefaultItemUpdater extends AbstractItemUpdater {

    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        decreaseQuality(item);
        if (isExpired(item)) {
            decreaseQuality(item);
        }
    }
}
