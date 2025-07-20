package com.example.backendtools.products.application.queries;

import java.util.Comparator;
import java.util.List;

import com.example.backendtools.products.application.ports.RankingCriteriaPort;
import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductRepository;
import com.example.backendtools.products.domain.RankingCriterion;
import com.example.backendtools.shared.domain.UseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class GetRankedProductsQueryHandler {
    private final ProductRepository productRepository;
    private final RankingCriteriaPort rankingCriteriaPort;

    public GetRankedProductsQueryHandler(ProductRepository productRepository, RankingCriteriaPort rankingCriteriaPort) {
        this.productRepository = productRepository;
        this.rankingCriteriaPort = rankingCriteriaPort;
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
                    RankingCriterion criterion = rankingCriteriaPort.findByName(criterionName);

                    if (criterion == null) {
                        log.warn("Criterion not found for name: {}", criterionName);
                        return 0.0;
                    }

                    double weight = weightEntry.getValue();
                    return criterion.calculateScore(product) * weight;
                })
                .sum();
    }
}
