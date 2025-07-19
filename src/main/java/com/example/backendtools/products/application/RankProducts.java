package com.example.backendtools.products.application;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new RankedProduct(product, calculateTotalScore(product, weights)))
                .sorted(Comparator.comparingDouble(RankedProduct::score).reversed())
                .toList();
    }

    private double calculateTotalScore(Product product, Map<String, Double> weights) {
        return weights.entrySet().stream()
                .mapToDouble(weightEntry -> {
                    String criterionName = weightEntry.getKey();
                    RankingCriterion criterion = criteria.get(criterionName);

                    if (criterion == null) {
                        return 0.0;
                    }

                    double weight = weightEntry.getValue();
                    return criterion.calculateScore(product) * weight;
                })
                .sum();
    }
}
