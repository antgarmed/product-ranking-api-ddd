package com.example.backendtools.products.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backendtools.products.application.ports.RankingCriteriaPort;
import com.example.backendtools.products.application.queries.GetRankedProductsQuery;
import com.example.backendtools.products.application.queries.GetRankedProductsQueryHandler;
import com.example.backendtools.products.application.queries.RankedProductResponse;
import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductRepository;
import com.example.backendtools.products.domain.ProductStock;
import com.example.backendtools.products.domain.SalesCriterion;
import com.example.backendtools.products.domain.StockRatioCriterion;

@ExtendWith(MockitoExtension.class)
class GetRankedProductsQueryHandlerTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private RankingCriteriaPort rankingCriteriaPort;

    @Mock
    private GetRankedProductsQueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        queryHandler = new GetRankedProductsQueryHandler(productRepository, rankingCriteriaPort);
        when(rankingCriteriaPort.findByName("sales")).thenReturn(new SalesCriterion());
        when(rankingCriteriaPort.findByName("stock")).thenReturn(new StockRatioCriterion());
    }

    @Test
    void shouldReturnProductsRankedByWeightedScore() {
        // Arrange
        Product product1 = Product.of(1, "V-NECH BASIC SHIRT", 100,
                ProductStock.of(Map.of("S", 4, "M", 9, "L", 0)));
        Product product2 = Product.of(2, "CONTRASTING FABRIC T-SHIRT", 50,
                ProductStock.of(Map.of("S", 35, "M", 9, "L", 9)));
        Product product3 = Product.of(3, "RAISED PRINT T-SHIRT", 80,
                ProductStock.of(Map.of("S", 20, "M", 2, "L", 20)));

        when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));

        GetRankedProductsQuery query = new GetRankedProductsQuery(Map.of(
                "sales", 0.8,
                "stock", 0.2));

        // Act
        List<RankedProductResponse> rankedProducts = queryHandler.execute(query);

        // Assert
        assertThat(rankedProducts).hasSize(3);
        assertThat(rankedProducts)
                .extracting(rp -> rp.product().getId())
                .containsExactly(1, 3, 2);
        assertThat(rankedProducts.get(0).score()).isEqualTo(80.4);
        assertThat(rankedProducts.get(1).score()).isEqualTo(64.6);
        assertThat(rankedProducts.get(2).score()).isEqualTo(40.6);
    }
}
