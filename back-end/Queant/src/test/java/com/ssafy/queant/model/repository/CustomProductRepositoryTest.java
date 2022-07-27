package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CustomProductRepositoryTest {

    @Autowired
    private CustomProductRepository customProductRepository;

    @Test
    public void 사용자정의예금상품저장(){
        //given
        final CustomProduct product = CustomProduct.builder()
                .institution_name("우리은행")
                .product_name("test1")
                .is_deposit(true)
                .base_rate(1.5)
                .start_date(new Date(System.currentTimeMillis()))
                .end_date(new Date(System.currentTimeMillis()+1000))
                .member_id(UUID.randomUUID())
                .build();

        //when
        final CustomProduct savedProduct = customProductRepository.save(product);

        //then
        assertEquals(product.getProduct_id(),savedProduct.getProduct_id());
    }

}