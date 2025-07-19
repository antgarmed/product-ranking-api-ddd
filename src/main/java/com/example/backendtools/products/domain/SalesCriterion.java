package com.example.backendtools.products.domain;

public class SalesCriterion implements RankingCriterion {
    @Override
    public int calculateScore(Product product) {
        return product.getSalesUnits();
    }
}
