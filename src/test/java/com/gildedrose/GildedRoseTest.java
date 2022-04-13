package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";

    @Test
    void updateQuality_shouldNotAlterTheItemName() {
        List<Item> items = List.of(new Item("foo", 0, 0));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).name).isEqualTo("foo");
    }

    @Test
    void updateQuality_shouldUpdateMultipleItems() {
        List<Item> items = List.of(new Item("foo", 1, 1), new Item("bar", 1, 1));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items)
            .hasSize(2)
            .allMatch((item) -> item.quality == 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"generic item", AGED_BRIE, BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT})
    void updateQuality_withAnyItem_shouldDecreaseSellInByOne(String itemName) {
        List<Item> items = List.of(new Item(itemName, -1, 13));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).sellIn).isEqualTo(-2);
    }

    @Test
    void updateQuality_withAgedBrieNonExpired_shouldIncreaseQualityBy1() {
        List<Item> items = List.of(new Item(AGED_BRIE, 1, 0));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(1);
    }

    @Test
    void updateQuality_withAgedBrieExpired_shouldIncreaseQualityBy2() {
        List<Item> items = List.of(new Item(AGED_BRIE, 0, 0));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(2);
    }

    @Test
    void updateQuality_withItemBeingNonExpired_shouldDecreaseQualityBy1() {
        List<Item> items = List.of(new Item("Bread", 1, 10));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(9);
    }

    @Test
    void updateQuality_withItemBeingExpired_shouldDecreaseQualityBy2() {
        List<Item> items = List.of(new Item("bread", 0, 10));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(8);
    }

    @Test
    void updateQuality_withItemSellIn0AndQuality0_thenQualityShouldNeverBeReducedBelowZero() {
        List<Item> items = List.of(new Item("bread", 0, 0));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {AGED_BRIE, BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT})
    void updateQuality_withAgedBrieAndQuality50_thenQualityShouldNeverBeIncreasedAbove50(String itemName) {
        List<Item> items = List.of(new Item(itemName, 2, 50));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(50);
    }

    @Test
    void updateQuality_withItemSulfuras_shouldNeverBeAltered() {
        List<Item> items = List.of(new Item(SULFURAS_HAND_OF_RAGNAROS, 29, 80));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        var expectedItem = new Item(SULFURAS_HAND_OF_RAGNAROS, 29, 80);
        assertThat(app.items.get(0)).usingRecursiveComparison().isEqualTo(expectedItem);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 20, 90})
    void updateQuality_withBackstagePassAndSellinGreaterThan10_shouldIncreaseQualityBy1(int sellIn) {
        List<Item> items = List.of(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, sellIn, 20));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(21);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 9, 8, 7, 6})
    void updateQuality_withBackstagePassAndSellinBetween6And10Inclusive_shouldIncreaseQualityBy2(int sellIn) {
        List<Item> items = List.of(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, sellIn, 20));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(22);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 4, 3, 2, 1})
    void updateQuality_withBackstagePassAndSellinBetween1And5Inclusive_shouldIncreaseQualityBy3(int sellIn) {
        List<Item> items = List.of(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, sellIn, 20));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isEqualTo(23);
    }

    @Test
    void updateQuality_withBackstagePassAndSellinOf0_shouldDecreaseQualityTo0() {
        List<Item> items = List.of(new Item(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 0, 20));
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items.get(0).quality).isZero();
    }

}
