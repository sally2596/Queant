package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Product;

import java.util.List;

public interface SearchRepository {
    public List<Product> searchSingle(boolean isDeposit, Boolean isSimpleInterest, Integer period, List<Integer> banks, List<String> joinway, List<String> conditions, List<String> traitSet);
}
