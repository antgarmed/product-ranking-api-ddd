package com.example.backendtools.products.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
public class Product {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter
    private int salesUnits;
    @Getter
    private ProductStock productStock;

    private Product(int id, String name, int salesUnits, ProductStock productStock) {
        this.id = id;
        this.name = name;
        this.salesUnits = salesUnits;
        this.productStock = productStock;
    }

    public static Product of(int id, String name, int salesUnits, ProductStock productStock) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank.");
        }

        if (salesUnits < 0) {
            throw new IllegalArgumentException("Sales unit cannot be negative");
        }

        if (productStock == null) {
            throw new IllegalArgumentException("Product stock cannot be null");
        }

        return new Product(id, name, salesUnits, productStock);
    }
}
