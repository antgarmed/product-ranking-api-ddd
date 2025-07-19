package com.example.backendtools.products.domain;

import com.example.backendtools.shared.domain.Criterion;

@Criterion("sales")
public class SalesCriterion implements RankingCriterion {
    @Override
    public int calculateScore(Product product) {
        return product.getSalesUnits();
    }
}
