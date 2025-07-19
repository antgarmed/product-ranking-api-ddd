package com.example.backendtools.products.domain;

public class StockRatioCriterion implements RankingCriterion {
    @Override
    public int calculateScore(Product product) {
        return product.countAvailableSizes();
    }
}
