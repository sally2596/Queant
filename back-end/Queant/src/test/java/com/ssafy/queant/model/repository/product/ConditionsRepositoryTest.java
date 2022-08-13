package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Conditions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ConditionsRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(ConditionsRepositoryTest.class);

    @Autowired
    private ConditionsRepository conditionsRepository;

    @Test
    void findByProductId() {
        int productId = 1;
        List<Conditions> list = conditionsRepository.findByProductId(productId);

        for (Conditions c : list
        ) {
            log.info(c.toString());
        }
    }
}