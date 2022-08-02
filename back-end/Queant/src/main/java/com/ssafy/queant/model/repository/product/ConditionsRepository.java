package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionsRepository extends JpaRepository<Conditions, Integer> {
    List<Conditions> findByProductId(String productId);
}
