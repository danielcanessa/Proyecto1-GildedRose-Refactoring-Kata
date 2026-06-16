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
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals(AGED_BRIE)
                    && !items[i].name.equals(BACKSTAGE_PASS)) {
                if (items[i].quality > MIN_QUALITY) {
                    if (!items[i].name.equals(SULFURAS)) {
                        decreaseQuality(items[i]);
                    }
                }
            } else {
                if (items[i].quality < MAX_QUALITY) {
                    increaseQuality(items[i]);

                    if (items[i].name.equals(BACKSTAGE_PASS)) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < MAX_QUALITY) {
                                increaseQuality(items[i]);
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < MAX_QUALITY) {
                                increaseQuality(items[i]);
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals(SULFURAS)) {
                decreaseSellIn(items[i]);
            }

            if (isExpired(items[i])) {
                if (!items[i].name.equals(AGED_BRIE)) {
                    if (!items[i].name.equals(BACKSTAGE_PASS)) {
                        if (items[i].quality > MIN_QUALITY) {
                            if (!items[i].name.equals(SULFURAS)) {
                                decreaseQuality(items[i]);
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < MAX_QUALITY) {
                        increaseQuality(items[i]);
                    }
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
