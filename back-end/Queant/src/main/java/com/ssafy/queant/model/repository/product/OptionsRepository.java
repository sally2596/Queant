package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options, Integer> {
    List<Options> findByProductId(String productId);
}
