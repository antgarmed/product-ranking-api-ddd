package com.example.backendtools.products.domain;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
