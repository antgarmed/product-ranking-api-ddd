package com.example.backendtools.products.infrastructure.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.backendtools.products.domain.RankingCriterion;
import com.example.backendtools.shared.domain.Criterion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RankingConfiguration {
    @Bean("rankingCriteriaMap")
    @Primary
    Map<String, RankingCriterion> rankingCriteriaMap(List<RankingCriterion> criteriaList) {
        return criteriaList.stream()
                .collect(Collectors.toMap(
                        criterion -> {
                            Criterion annotation = criterion.getClass().getAnnotation(Criterion.class);
                            if (annotation == null) {
                                throw new IllegalStateException("Criterion bean " + criterion.getClass().getName()
                                        + " is missing @Criterion annotation.");
                            }
                            return annotation.value();
                        },
                        Function.identity()));
    }
}
