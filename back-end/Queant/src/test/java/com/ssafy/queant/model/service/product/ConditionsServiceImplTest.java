package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ConditionsDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ConditionsServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(ConditionsServiceImplTest.class);

    @Autowired
    private ConditionsService conditionsService;

    @Test
    void findByProductId() {
        int productId = 1;
        List<ConditionsDto> result = conditionsService.findByProductId(productId);

        for (ConditionsDto dto : result
        ) {
            log.info(dto.toString());
        }
    }
}