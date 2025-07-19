package com.example.backendtools.products.domain;

import com.example.backendtools.shared.domain.Criterion;

@Criterion("stock")
public class StockRatioCriterion implements RankingCriterion {
    @Override
    public int calculateScore(Product product) {
        return product.countAvailableSizes();
    }
}
