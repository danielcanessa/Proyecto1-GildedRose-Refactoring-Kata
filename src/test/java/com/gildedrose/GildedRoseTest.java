package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    // Verifica la degradacion normal antes y despues del vencimiento, incluido el limite minimo.
    @ParameterizedTest(name = "[{index}] {0}: sellIn {1} -> {3}, quality {2} -> {4}")
    @MethodSource("normalItemCases")
    void normalItemDegradesAccordingToExpiration(
            String name,
            int initialSellIn,
            int initialQuality,
            int expectedSellIn,
            int expectedQuality) {
        assertUpdatedItem(name, initialSellIn, initialQuality, expectedSellIn, expectedQuality);
    }

    private static Stream<Arguments> normalItemCases() {
        return Stream.of(
                Arguments.of("Normal Item", 5, 10, 4, 9),
                Arguments.of("Normal Item", 0, 10, -1, 8),
                Arguments.of("Normal Item", 0, 1, -1, 0)
        );
    }

    // Verifica que Aged Brie mejore mas rapido despues del vencimiento sin cambiar su identidad.
    @ParameterizedTest(name = "[{index}] sellIn {0} -> {2}, quality {1} -> {3}")
    @MethodSource("agedBrieCases")
    void agedBrieImprovesAccordingToExpiration(
            int initialSellIn,
            int initialQuality,
            int expectedSellIn,
            int expectedQuality) {
        assertUpdatedItem(
                AGED_BRIE,
                initialSellIn,
                initialQuality,
                expectedSellIn,
                expectedQuality
        );
    }

    private static Stream<Arguments> agedBrieCases() {
        return Stream.of(
                Arguments.of(5, 10, 4, 11),
                Arguments.of(0, 10, -1, 12)
        );
    }

    // Evalua los limites exactos de Backstage: 10 dias, 5 dias y la fecha del concierto.
    @ParameterizedTest(name = "[{index}] sellIn {0} -> {2}, quality {1} -> {3}")
    @MethodSource("backstagePassCases")
    void backstagePassChangesAtConcertThresholds(
            int initialSellIn,
            int initialQuality,
            int expectedSellIn,
            int expectedQuality) {
        assertUpdatedItem(
                BACKSTAGE_PASS,
                initialSellIn,
                initialQuality,
                expectedSellIn,
                expectedQuality
        );
    }

    private static Stream<Arguments> backstagePassCases() {
        return Stream.of(
                Arguments.of(11, 20, 10, 21),
                Arguments.of(10, 20, 9, 22),
                Arguments.of(6, 20, 5, 22),
                Arguments.of(5, 20, 4, 23),
                Arguments.of(0, 20, -1, 0)
        );
    }

    // Confirma que los articulos que mejoran se detengan en la calidad maxima de 50.
    @ParameterizedTest(name = "[{index}] {0}: sellIn {1} -> {3}, quality {2} -> {4}")
    @MethodSource("maximumQualityCases")
    void improvingItemsNeverExceedMaximumQuality(
            String name,
            int initialSellIn,
            int initialQuality,
            int expectedSellIn,
            int expectedQuality) {
        assertUpdatedItem(name, initialSellIn, initialQuality, expectedSellIn, expectedQuality);
    }

    private static Stream<Arguments> maximumQualityCases() {
        return Stream.of(
                Arguments.of(AGED_BRIE, 0, 49, -1, 50),
                Arguments.of(BACKSTAGE_PASS, 5, 49, 4, 50)
        );
    }

    // Sulfuras es legendario, por lo que no deben cambiar sellIn ni quality.
    @Test
    void sulfurasNeverChanges() {
        assertUpdatedItem(SULFURAS, 0, 80, 0, 80);
    }

    // Actualizar un inventario vacio debe ser una operacion valida sin efectos.
    @Test
    void emptyInventoryCanBeUpdated() {
        Item[] items = new Item[] {};
        GildedRose gildedRose = new GildedRose(items);

        assertDoesNotThrow(gildedRose::updateQuality);
        assertEquals(0, gildedRose.items.length);
    }

    // Crea un inventario nuevo y verifica el estado completo del articulo despues de un dia.
    private static void assertUpdatedItem(
            String name,
            int initialSellIn,
            int initialQuality,
            int expectedSellIn,
            int expectedQuality) {
        Item item = new Item(name, initialSellIn, initialQuality);
        GildedRose gildedRose = new GildedRose(new Item[] {item});

        gildedRose.updateQuality();

        assertEquals(name, item.name);
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }
}
