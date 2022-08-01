package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByBankId() {
        int wooriBankId = 10345;
        List<Product> list = productRepository.findByBankId(wooriBankId);

        log.info("*****[start logging product list]*****");
        for (Product p:list) {
            log.info(p.toString());
        }
    }
}