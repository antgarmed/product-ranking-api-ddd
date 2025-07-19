package com.example.backendtools.products.application.queries;

import com.example.backendtools.products.domain.Product;

public record RankedProductResponse(Product product, double score) {
}
