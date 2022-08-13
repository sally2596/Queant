package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.TraitSetDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class TraitSetServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(TraitSetServiceImplTest.class);

    @Autowired
    private TraitSetService traitSetService;

    @Test
    void findByProductId() {
        int productId = 1;
        List<TraitSetDto> list = traitSetService.findByProductId(productId);
        for (TraitSetDto t : list
        ) {
            log.info(t.toString());
        }
    }
}