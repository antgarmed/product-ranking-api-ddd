package com.example.backendtools.products.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class SalesCriterionTest {
    @Test
    void shouldReturnProductSalesUnitsAsScore() {
        // Arrange
        int id = 1;
        String name = "Valid Name";
        int salesUnits = 100;
        ProductStock stock = ProductStock.of(Map.of(
                "S", 50,
                "M", 0,
                "L", 10));
        Product product = Product.of(id, name, salesUnits, stock);

        RankingCriterion salesCriterion = new SalesCriterion();

        // Act
        int score = salesCriterion.calculateScore(product);

        // Assert
        assertThat(score).isEqualTo(salesUnits);
    }
}
