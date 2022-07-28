package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomProductRepository extends JpaRepository<CustomProduct, String> {

//    @Query("select c from CustomProduct c left join Member m on m.member_id=c.memberId where m.email=:email")
//    List<CustomProduct> findByMemberEmail(@Param("email") String email);

    List<CustomProduct> findByMemberId(UUID memberId);
    Optional<CustomProduct> findByProductId(int productId);
}
