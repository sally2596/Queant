package com.ssafy.queant.model.repository.product;

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
        List<Product> list = productRepository.findByBankIdAndIsEnabledTrue(wooriBankId);

        log.info("*****[start logging product list]*****");
        for (Product p : list) {
            log.info(p.toString());
        }
    }

    @Test
    void insert() {
        Product product = Product.builder()
                .productCode("aa")
                .bankId(10345)
                .name("된 걸까..")
                .scodeId("A001")
                .isDeposit(true)
                .isEnabled(false)
                .build();

        Product saved = productRepository.save(product);
        log.info(saved.toString());
    }

    @Test
    void findByNameContaining() {
        String name = "KB";
        List<Product> list = productRepository.findByIsEnabledTrueAndNameContaining(name);

        log.info("*****[start logging product list]*****");
        for (Product p : list) {
            log.info(p.toString());
        }
    }

    @Test
    void findByIsEnabledFalse() {
        List<Product> list = productRepository.findByIsEnabledFalse();
        for (Product p : list) {
            log.info(p.toString());
        }
    }
}