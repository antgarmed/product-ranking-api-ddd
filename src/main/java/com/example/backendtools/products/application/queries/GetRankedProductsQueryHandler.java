package com.example.backendtools.products.application.queries;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductRepository;
import com.example.backendtools.products.domain.RankingCriterion;

public class GetRankedProductsQueryHandler {
    private final ProductRepository productRepository;
    private final Map<String, RankingCriterion> criteria;

    public GetRankedProductsQueryHandler(ProductRepository productRepository, Map<String, RankingCriterion> criteria) {
        this.productRepository = productRepository;
        this.criteria = criteria;
    }

    public List<RankedProductResponse> execute(GetRankedProductsQuery query) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new RankedProductResponse(product, calculateTotalScore(product, query)))
                .sorted(Comparator.comparingDouble(RankedProductResponse::score).reversed())
                .toList();
    }

    private double calculateTotalScore(Product product, GetRankedProductsQuery query) {
        return query.weights().entrySet().stream()
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
