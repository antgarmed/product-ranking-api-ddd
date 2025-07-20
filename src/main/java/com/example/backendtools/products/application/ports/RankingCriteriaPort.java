package com.example.backendtools.products.application.ports;

import com.example.backendtools.products.domain.RankingCriterion;

public interface RankingCriteriaPort {
    RankingCriterion findByName(String name);
}
