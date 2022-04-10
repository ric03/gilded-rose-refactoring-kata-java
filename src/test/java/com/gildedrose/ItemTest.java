package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void testToString() {
        // Arrange
        var item = new Item("foo", 3, 13);
        // Act
        var result = item.toString();
        // Assert
        assertThat(result).isEqualTo("foo, 3, 13");
    }
}
