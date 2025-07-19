package com.example.backendtools.products.infrastructure.api.rest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WeightsParser {
    private WeightsParser() {
    }

    public static Map<String, Double> parse(String weightsStr) {
        if (weightsStr == null || weightsStr.isBlank()) {
            return Map.of();
        }

        try {
            return Arrays.stream(weightsStr.split(","))
                    .map(pair -> pair.split(":"))
                    .collect(Collectors.toMap(
                            parts -> parts[0].trim(),
                            parts -> Double.parseDouble(parts[1].trim())));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid weights format. Expected 'key1:value1,key2:value2'.");
        }
    }
}
