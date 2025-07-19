package com.example.backendtools.products.application;

import com.example.backendtools.products.domain.Product;

public record RankedProduct(Product product, double score) {
}
