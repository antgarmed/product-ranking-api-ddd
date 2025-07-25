package com.example.backendtools.products.domain;

import java.util.Map;
import java.util.Objects;

import lombok.Value;

@Value
public final class ProductStock {
    Map<String, Integer> sizeQuantities;

    private ProductStock(Map<String, Integer> sizeQuantities) {
        this.sizeQuantities = sizeQuantities;
    }

    public static ProductStock of(Map<String, Integer> sizeQuantities) {
        Objects.requireNonNull(sizeQuantities, "Size quantities map cannot be null");

        boolean hasNegativeStock = sizeQuantities.values().stream()
                .anyMatch(quantity -> quantity < 0);

        if (hasNegativeStock) {
            throw new IllegalArgumentException("Stock quantities cannot be negative");
        }

        return new ProductStock(Map.copyOf(sizeQuantities));
    }

    public Map<String, Integer> getSizeQuantities() {
        return sizeQuantities;
    }

    public int countAvailableSizes() {
        return (int) sizeQuantities.values().stream()
                .filter(quantity -> quantity > 0)
                .count();
    }
}
