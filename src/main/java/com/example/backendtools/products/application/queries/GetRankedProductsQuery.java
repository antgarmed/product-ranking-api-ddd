package com.example.backendtools.products.application.queries;

import java.util.Map;

public record GetRankedProductsQuery(Map<String, Double> weights) {
}
