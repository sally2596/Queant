package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Joinway;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JoinwayRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(JoinwayRepositoryTest.class);

    @Autowired
    private JoinwayRepository joinwayRepository;

    @Test
    void findByProductId() {
        int productId = 1;
        List<Joinway> list = joinwayRepository.findByProductId(productId);

        for (Joinway j : list
        ) {
            log.info(j.toString());
        }
    }
}