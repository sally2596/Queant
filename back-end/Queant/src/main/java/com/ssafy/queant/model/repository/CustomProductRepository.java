package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomProductRepository extends JpaRepository<CustomProduct, String> {

    @Query("select c from CustomProduct c left join Member m on m.member_id=c.member_id where m.email=:email")
    List<CustomProduct> findByMemberEmail(@Param("email") String email);
}
