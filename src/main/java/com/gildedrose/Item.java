package com.gildedrose;

// Encapsula los datos de un articulo del inventario. El estado solo se modifica
// a traves de los setters, de modo que las estrategias de actualizacion respetan
// las reglas del negocio sin exponer los campos directamente.
public final class Item {

    private final String name;
    private int sellIn;
    private int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return name + ", " + sellIn + ", " + quality;
    }
}
