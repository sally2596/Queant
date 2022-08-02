package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Joinway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinwayRepository extends JpaRepository<Joinway, Integer> {
    List<Joinway> findByProductId(String productId);
}
