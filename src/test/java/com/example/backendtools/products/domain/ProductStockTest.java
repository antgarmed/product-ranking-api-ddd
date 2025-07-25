package com.example.backendtools.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ProductStockTest {
    @Test
    void shouldReturnCorrectCountOfSizesWithStockWhenOneSizeIsZero() {
        // Arrange
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));

        // Act
        int availableSizes = stock.countAvailableSizes();

        // Assert
        assertThat(availableSizes).isEqualTo(2);
    }

    @Test
    void shouldThrowExceptionWhenStockIsCreatedWithNegativeQuantity() {
        // Arrange
        var invalidStockMap = Map.of(
                "S", -50,
                "M", 0,
                "L", 0);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductStock.of(invalidStockMap));
    }
}
