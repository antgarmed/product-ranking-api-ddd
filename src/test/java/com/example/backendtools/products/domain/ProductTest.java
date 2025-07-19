package com.example.backendtools.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ProductTest {
    @Test
    void shouldCreateProductWithCorrectAttributes() {
        // Arrange
        int id = 1;
        String name = "Valid Name";
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));

        // Act
        Product product = Product.of(id, name, salesUnits, stock);

        // Assert
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getSalesUnits()).isEqualTo(salesUnits);
        assertThat(product.getProductStock()).isEqualTo(stock);
    }

    @Test
    void shouldThrowExceptionWhenProductIsCreatedWithNonPositiveId() {
        // Arrange
        int invalidId = 0;
        String name = "Valid Name";
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Product.of(invalidId, name, salesUnits, stock);
        });
    }

    @Test
    void shouldThrowExceptionWhenProductIsCreatedWithNullName() {
        // Arrange
        int id = 1;
        String invalidName = null;
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Product.of(id, invalidName, salesUnits, stock);
        });
    }

    @Test
    void shouldThrowExceptionWhenProductIsCreatedWithEmptyName() {
        // Arrange
        int id = 1;
        String invalidName = "";
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of("S", 10));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Product.of(id, invalidName, salesUnits, stock);
        });
    }

    @Test
    void shouldThrowExceptionWhenProductIsCreatedWithNegativeSalesUnits() {
        // Arrange
        int id = 1;
        String name = "Valid Name";
        int invalidSalesUnits = -1;
        ProductStock stock = ProductStock.of(Map.of("S", 10));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Product.of(id, name, invalidSalesUnits, stock);
        });
    }

    @Test
    void shouldThrowExceptionWhenProductIsCreatedWithNullStock() {
        // Arrange
        int id = 1;
        String name = "Valid Name";
        int salesUnits = 100;
        ProductStock invalidStock = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Product.of(id, name, salesUnits, invalidStock);
        });
    }

    @Test
    void shouldReturnCorrectCountOfAvailableSizesFromItsStock() {
        // Arrange
        int id = 1;
        String name = "Valid Name";
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));
        Product product = Product.of(id, name, salesUnits, stock);

        // Act
        int availableSizes = product.countAvailableSizes();

        // Assert
        assertThat(availableSizes).isEqualTo(2);
    }
}
