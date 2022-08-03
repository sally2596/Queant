package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    // 단품 검색 (검색어 조합) == 추천

    // 상품 검색 (텍스트 검색, 상품명으로 검색)
    List<Product> findByNameContaining(@Param("name") String name);

    // 세트 검색 (검색어 조합) == 추천

    // 서비스에서 공식적으로 제공하는 모든 상품 == isEnabled가 true인 상품

    // 은행별로 묶어서 제공
    List<Product> findByBankId(int bankId);
}
