package com.example.backendtools.products.infrastructure.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductRepository;
import com.example.backendtools.products.domain.ProductStock;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = List.of(
            Product.of(
                    1,
                    "V-NECH BASIC SHIRT",
                    100,
                    ProductStock.of(Map.of("S", 4, "M", 9, "L", 0))),
            Product.of(
                    2, "CONTRASTING FABRIC T-SHIRT",
                    50,
                    ProductStock.of(Map.of("S", 35, "M", 9, "L", 9))),
            Product.of(
                    3,
                    "RAISED PRINT T-SHIRT",
                    80,
                    ProductStock.of(Map.of("S", 20, "M", 2, "L", 20))),
            Product.of(
                    4,
                    "PLEATED T-SHIRT",
                    3,
                    ProductStock.of(Map.of("S", 25, "M", 30, "L", 10))),
            Product.of(
                    5,
                    "CONTRASTING LACE T-SHIRT",
                    650,
                    ProductStock.of(Map.of("S", 0, "M", 1, "L", 0))),
            Product.of(
                    6,
                    "SLOGAN T-SHIRT",
                    20,
                    ProductStock.of(Map.of("S", 9, "M", 2, "L", 5))));

    @Override
    public List<Product> findAll() {
        return products;
    }
}
