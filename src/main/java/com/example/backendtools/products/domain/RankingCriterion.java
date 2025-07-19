package com.example.backendtools.products.domain;

public interface RankingCriterion {
    int calculateScore(Product product);
}
