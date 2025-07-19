package com.example.backendtools.products.application;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductRepository;
import com.example.backendtools.products.domain.RankingCriterion;

public class RankProducts {
    private final ProductRepository productRepository;
    private final Map<String, RankingCriterion> criteria;

    public RankProducts(ProductRepository productRepository, Map<String, RankingCriterion> criteria) {
        this.productRepository = productRepository;
        this.criteria = criteria;
    }

    public List<RankedProduct> execute(Map<String, Double> weights) {
        var products = productRepository.findAll().stream()
                .map(product -> {
                    double score = 0;

                    for (Entry<String, Double> weight : weights.entrySet()) {
                        var criterion = criteria.get(weight.getKey());
                        score += criterion.calculateScore(product) * weight.getValue();
                    }

                    return new RankedProduct(product, score);
                })
                .sorted((product1, product2) -> product1.score() > product2.score() ? -1 : 1)
                .toList();

        return products;
    }

    public record RankedProduct(Product product, double score) {
    }
}
