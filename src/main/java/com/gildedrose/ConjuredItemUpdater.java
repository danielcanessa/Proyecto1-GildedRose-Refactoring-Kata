package com.gildedrose;

// Articulos conjurados: se degradan al doble de velocidad que uno normal
// (-2 por dia y -4 una vez vencidos).
public class ConjuredItemUpdater extends AbstractItemUpdater {

    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        decreaseQuality(item);
        decreaseQuality(item);
        if (isExpired(item)) {
            decreaseQuality(item);
            decreaseQuality(item);
        }
    }
}
