package com.gildedrose;

// Backstage passes: la calidad sube mas rapido conforme se acerca el concierto
// (+2 a 10 dias o menos, +3 a 5 dias o menos) y cae a cero una vez pasado el evento.
public class BackstagePassUpdater extends AbstractItemUpdater {

    private static final int DOUBLE_GAIN_THRESHOLD = 10;
    private static final int TRIPLE_GAIN_THRESHOLD = 5;

    @Override
    public void update(Item item) {
        decreaseSellIn(item);

        if (isExpired(item)) {
            resetQuality(item);
            return;
        }

        increaseQuality(item);
        if (item.getSellIn() < DOUBLE_GAIN_THRESHOLD) {
            increaseQuality(item);
        }
        if (item.getSellIn() < TRIPLE_GAIN_THRESHOLD) {
            increaseQuality(item);
        }
    }
}
