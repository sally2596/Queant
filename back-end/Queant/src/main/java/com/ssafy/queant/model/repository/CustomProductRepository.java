package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomProductRepository extends JpaRepository<CustomProduct, String> {

    @Query("select p from CustomProduct p,Member m join p.member_id where m.email =:email")
    List<CustomProduct> findByEmail(@Param("email") String email);

}
