package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.JoinwayDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JoinwayServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(JoinwayServiceImplTest.class);

    @Autowired
    private JoinwayService joinwayService;

    @Test
    void findByProductId() {
        int productId = 1;
        List<JoinwayDto> result = joinwayService.findByProductId(productId);

        for (JoinwayDto dto : result
        ) {
            log.info(dto.toString());
        }
    }

}