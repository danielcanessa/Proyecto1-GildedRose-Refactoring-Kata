package com.gildedrose;

class GildedRose {
    // Nombres exactos de articulos especiales usados para aplicar reglas particulares.
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    // Limites de calidad permitidos por las reglas del inventario.
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            boolean isAgedBrie = item.name.equals(AGED_BRIE);
            boolean isBackstagePass = item.name.equals(BACKSTAGE_PASS);
            boolean isSulfuras = item.name.equals(SULFURAS);
            boolean isNormalItem = !isAgedBrie && !isBackstagePass && !isSulfuras;
            boolean shouldDecreasesSellIn = !isSulfuras;

            // Aplica los cambios de calidad antes de reducir sellIn.
            if (isNormalItem) {
                decreaseQuality(item);
            } else {
                if (isAgedBrie || isBackstagePass) {
                    increaseQuality(item);

                    if (isBackstagePass) {
                        if (item.sellIn < 11) {
                            increaseQuality(item);
                        }

                        if (item.sellIn < 6) {
                            increaseQuality(item);
                        }
                    }
                }
            }

            // Reduce sellIn solo para articulos que no son legendarios.
            if (shouldDecreasesSellIn) {
                decreaseSellIn(item);
            }

            // Aplica reglas adicionales cuando el articulo ya vencio.
            if (isExpired(item)) {
                if (isAgedBrie) {
                    increaseQuality(item);
                } else if (isBackstagePass) {
                    item.quality = MIN_QUALITY;
                } else if (isNormalItem) {
                    decreaseQuality(item);
                }
            }
        }
    }

    // Aumenta la calidad sin superar el maximo permitido.
    private void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    // Reduce la calidad sin bajar del minimo permitido.
    private void decreaseQuality(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality = item.quality - 1;
        }
    }

    // Reduce los dias restantes de venta para articulos no legendarios.
    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    // Indica si el articulo ya paso su fecha de venta.
    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }
}
