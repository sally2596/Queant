package com.ssafy.queant.model.repository.product;

import com.querydsl.core.Tuple;

import java.util.List;

public interface SearchRepository {
    public List<Tuple> searchSingle(Long amount, boolean isDeposit, Boolean isSimpleInterest, Boolean isFixed, Integer period, List<Integer> banks, List<String> joinway, List<String> conditions, List<String> traitSet);
}
