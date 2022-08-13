package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.OptionsDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OptionsServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(OptionsServiceImplTest.class);

    @Autowired
    private OptionsService optionsService;

    @Test
    void findByProductId() {
        int productId = 1;
        List<OptionsDto> result = optionsService.findByProductId(productId);

        for (OptionsDto dto : result
        ) {
            log.info(dto.toString());
        }
    }
}