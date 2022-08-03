package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @Autowired
    private ProductService productService;

    @Test
    void findByNameContaining() {
        String name = "우리 SUPER정기예금";

        List<ProductDto> list = productService.findByNameContaining(name);
        for (ProductDto p:list
             ) {
            log.info(p.toString());
        }
    }
}