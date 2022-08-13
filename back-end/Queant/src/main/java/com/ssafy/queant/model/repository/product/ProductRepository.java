package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    //productId로 상품 하나 검색
    Optional<Product> findByProductId(String productId);

    // 상품 검색 (텍스트 검색, 상품명으로 검색) -> impl에서 querydsl로 구현함
    @Deprecated
    List<Product> findByIsEnabledTrueAndNameContaining(@Param("name") String name);

    // admin이 사용자들이 입력한 상품 확인(isEnabled = false && scodeId = "E003"인 상품 )
    List<Product> findByIsEnabledFalse();

    // 은행별로 묶어서 제공 -> impl에서 querydsl로 구현함
    @Deprecated
    List<Product> findByBankIdAndIsEnabledTrue(int bankId);

    // 상품 세부 정보 제공
    Optional<Product> findByProductId(int productId);

    @Query(value = "select LAST_INSERT_ID()", nativeQuery = true)
    int lastInsertId();

}
