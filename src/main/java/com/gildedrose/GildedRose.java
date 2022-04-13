package com.gildedrose;

class GildedRose {
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateQualityOfItem(item);
        }
    }

    private void updateQualityOfItem(Item item) {
        switch (item.name) {
            case AGED_BRIE -> updateAgedBrie(item);
            case BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT -> updateBackStagePass(item);
            case SULFURAS_HAND_OF_RAGNAROS -> noop();
            default -> updateRegular(item);
        }
    }

    private void noop() {
        // noop
    }

    private void updateRegular(Item item) {
        if (item.quality > 0) {
            item.quality -= 1;
        }

        reduceSellInValueOf(item);

        if (item.sellIn < 0 && item.quality > 0) {
            item.quality -= 1;
        }
    }

    private void updateAgedBrie(Item item) {
        if (item.quality < 50) {
            item.quality += 1;
        }

        reduceSellInValueOf(item);

        if (item.sellIn < 0 && item.quality < 50) {
            item.quality += 1;
        }
    }

    private void updateBackStagePass(Item item) {
        if (item.quality < 50) {
            item.quality += 1;
        }

        if (item.quality < 50) {
            if (item.sellIn < 11) {
                item.quality += 1;
            }

            if (item.sellIn < 6) {
                item.quality += 1;
            }
        }

        reduceSellInValueOf(item);

        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void reduceSellInValueOf(Item item) {
        item.sellIn -= 1;
    }
}
