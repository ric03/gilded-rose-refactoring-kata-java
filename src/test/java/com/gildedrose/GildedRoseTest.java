package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @Test
    void updateQuality_shouldNotAlterTheItemName() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].name).isEqualTo("foo");
    }

    @Test
    void updateQuality_shouldUpdateMultipleItems() {
        Item[] items = new Item[]{new Item("foo", 1, 1), new Item("bar", 1, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items)
            .hasSize(2)
            .allMatch((item) -> item.quality == 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"generic item", "Aged Brie", "Backstage passes to a TAFKAL80ETC concert"})
    void updateQuality_withAnyItem_shouldDecreaseSellInByOne(String itemName) {
        Item[] items = new Item[]{new Item(itemName, -1, 13)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].sellIn).isEqualTo(-2);
    }

    @Test
    void updateQuality_withAgedBrieNonExpired_shouldIncreaseQualityBy1() {
        Item[] items = new Item[]{new Item("Aged Brie", 1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(1);
    }

    @Test
    void updateQuality_withAgedBrieExpired_shouldIncreaseQualityBy2() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(2);
    }

    @Test
    void updateQuality_withItemBeingNonExpired_shouldDecreaseQualityBy1() {
        Item[] items = new Item[]{new Item("Bread", 1, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(9);
    }

    @Test
    void updateQuality_withItemBeingExpired_shouldDecreaseQualityBy2() {
        Item[] items = new Item[]{new Item("bread", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(8);
    }

    @Test
    void updateQuality_withItemSellIn0AndQuality0_thenQualityShouldNeverBeReducedBelowZero() {
        Item[] items = new Item[]{new Item("bread", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aged Brie", "Backstage passes to a TAFKAL80ETC concert"})
    void updateQuality_withAgedBrieAndQuality50_thenQualityShouldNeverBeIncreasedAbove50(String itemName) {
        Item[] items = new Item[]{new Item(itemName, 2, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(50);
    }

    @Test
    void updateQuality_withItemSulfuras_shouldNeverBeAltered() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 29, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        var expectedItem = new Item("Sulfuras, Hand of Ragnaros", 29, 80);
        assertThat(app.items[0]).usingRecursiveComparison().isEqualTo(expectedItem);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 20, 90})
    void updateQuality_withBackstagePassAndSellinGreaterThan10_shouldIncreaseQualityBy1(int sellIn) {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(21);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 9, 8, 7, 6})
    void updateQuality_withBackstagePassAndSellinBetween6And10Inclusive_shouldIncreaseQualityBy2(int sellIn) {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(22);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 4, 3, 2, 1})
    void updateQuality_withBackstagePassAndSellinBetween1And5Inclusive_shouldIncreaseQualityBy3(int sellIn) {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isEqualTo(23);
    }

    @Test
    void updateQuality_withBackstagePassAndSellinOf0_shouldDecreaseQualityTo0() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(app.items[0].quality).isZero();
    }

}
