package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.TraitSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraitSetRepository extends JpaRepository<TraitSet, Integer> {
    List<TraitSet> findByProductId(int productId);
}
