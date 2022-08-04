package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    // 단품 검색 (검색어 조합) == 추천


    // 상품 검색 (텍스트 검색, 상품명으로 검색)
    List<Product> findByIsEnabledTrueAndNameContaining(@Param("name") String name);

    List<Product> findByNameContaining(@Param("name") String name);

    // 세트 검색 (검색어 조합) == 추천

    // admin이 사용자들이 입력한 상품 확인(isEnabled = false && scodeId = "E003"인 상품 )
    List<Product> findByIsEnabledFalse();

    // 은행별로 묶어서 제공
    List<Product> findByBankIdAndIsEnabledTrue(int bankId);

    List<Product> findByBankId(int bankId);

    // 상품 세부 정보 제공
    Optional<Product> findByProductId(int productId);
}
