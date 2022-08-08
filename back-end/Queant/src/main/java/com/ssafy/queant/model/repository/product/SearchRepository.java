package com.ssafy.queant.model.repository.product;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {
    public Page<Tuple> searchSingle(Long amount, boolean isDeposit, Boolean isSimpleInterest, Boolean isFixed, Integer period, List<Integer> banks, List<String> joinway, List<String> conditions, List<String> traitSet, Pageable pageable);
}
