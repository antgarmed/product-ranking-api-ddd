package com.example.backendtools.products.infrastructure.api.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendtools.products.application.queries.GetRankedProductsQuery;
import com.example.backendtools.products.application.queries.GetRankedProductsQueryHandler;
import com.example.backendtools.products.application.queries.RankedProductResponse;

@RestController
public class GetRankedProductsEndpoint {
    private final GetRankedProductsQueryHandler queryHandler;

    public GetRankedProductsEndpoint(GetRankedProductsQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @GetMapping("/products")
    public List<RankedProductResponse> handle(@RequestParam("weights") String weights) {
        Map<String, Double> parsedWeights = WeightsParser.parse(weights);
        GetRankedProductsQuery query = new GetRankedProductsQuery(parsedWeights);
        return queryHandler.execute(query);
    }
}
