package com.example.backendtools.products.infrastructure.adapters;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.backendtools.products.application.ports.RankingCriteriaPort;
import com.example.backendtools.products.domain.RankingCriterion;

@Component
public class RankingCriteriaAdapter implements RankingCriteriaPort {
    private final Map<String, RankingCriterion> criteriaMap;

    public RankingCriteriaAdapter(@Qualifier("rankingCriteriaMap") Map<String, RankingCriterion> criteriaMap) {
        this.criteriaMap = criteriaMap;
    }

    @Override
    public RankingCriterion findByName(String name) {
        return criteriaMap.get(name);
    }
}
