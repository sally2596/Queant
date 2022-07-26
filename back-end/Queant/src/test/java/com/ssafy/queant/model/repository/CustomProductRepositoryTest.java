package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomProductRepositoryTest {

    @Autowired
    private CustomProductRepository customProductRepository;

    @Test
    public void save(){
        //given
        final CustomProduct product = CustomProduct.builder()
                .institution_name("우리은행")
                .product_name("test1")
                .is_deposit(true)
                .base_rate(1.5)
                .start_date(null)
                .end_date(null)
                .member_id(UUID.randomUUID())
                .build();

        //when
        final CustomProduct savedProduct = customProductRepository.save(product);

        //then
        assertEquals(product.getProduct_id(),savedProduct.getProduct_id());
    }

}