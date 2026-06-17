package com.gildedrose;

// Punto de entrada de demostracion: ejecuta varios dias de inventario para
// observar como cada tipo de articulo evoluciona segun su estrategia.
public class Main {

    public static void main(String[] args) {
        Item[] items = new Item[] {
                new Item("Aged Brie", 2, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Conjured Mana Cake", 3, 6),
                new Item("Normal Item", 10, 20)
        };

        GildedRose app = new GildedRose(items);

        for (int day = 0; day < 5; day++) {
            System.out.println("Dia " + day);
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println("--------------------");
            app.updateQuality();
        }
    }
}
