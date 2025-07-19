package com.example.backendtools.products.infrastructure.api.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.backendtools.products.application.queries.GetRankedProductsQuery;
import com.example.backendtools.products.application.queries.GetRankedProductsQueryHandler;
import com.example.backendtools.products.application.queries.RankedProductResponse;
import com.example.backendtools.products.domain.Product;
import com.example.backendtools.products.domain.ProductStock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetRankedProductsEndpoint.class)
public class GetRankedProductsEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetRankedProductsQueryHandler queryHandler;

    @Test
    void shouldReturnRankedProductsWhenWeightsAreProvided() throws Exception {
        // Arrange
        Product product = Product.of(
                1,
                "Test Product",
                100,
                ProductStock.of(Map.of("S", 1, "L", 0, "M", 0)));
        List<RankedProductResponse> mockResponse = List.of(new RankedProductResponse(product, 100.0));

        when(queryHandler.execute(any(GetRankedProductsQuery.class))).thenReturn(mockResponse);

        // Act
        ResultActions result = mockMvc.perform(get("/products")
                .queryParam("weights", "sales:0.8,stock:0.2")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.id").value(1))
                .andExpect(jsonPath("$[0].score").value(100.0));
    }
}
