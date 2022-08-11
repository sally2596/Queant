package com.ssafy.queant.model.repository.product;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductRepositoryImplTest {
    private final Logger log = LoggerFactory.getLogger(ProductRepositoryImplTest.class);

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Test
    void findByIsEnabledTrueAndNameContaining() {
        String name = "KB";
        List<Tuple> list = productRepository.findByIsEnabledTrueAndNameContaining(name);

        log.info("*****[start logging product list]*****");
        int cnt = 0;
        for (Tuple p : list) {
            log.info(p.toString());
            cnt++;
        }
        log.info(String.valueOf(cnt));
    }

    @Test
    void findByIsEnabledTrueAndBankId() {
        int id = 17801;
        List<Tuple> list = productRepository.findByBankIdAndIsEnabledTrue(id);

        log.info("*****[start logging product list]*****");
        int cnt = 0;
        for (Tuple p : list) {
            log.info(p.toString());
            cnt++;
        }
        log.info(String.valueOf(cnt));
    }
}