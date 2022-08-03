package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ProductDetailDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @Autowired
    private ProductService productService;

    @Test
    void findByNameContaining() {
        String name = "우리 SUPER정기예금";

        List<ProductDto> list = productService.findByNameContaining(name);
        for (ProductDto p : list
        ) {
            log.info(p.toString());
        }
    }

    @Test
    void findByProductId() {
        String productId = "0051";

        ProductDetailDto productDetailDto = productService.findByProductId(productId);
        if (productDetailDto == null) log.info("null값 들어옴");
        else log.info(productDetailDto.toString());
    }

    @Test
    void registProduct() {
        ProductDto product = ProductDto.builder()
                .productId("aa")
                .bankId(10345)
                .name("약오르지 까꿍")
                .scodeId("A001")
                .isDeposit(true)
                .isEnabled(false)
                .build();

        ProductDto saved = productService.registProduct(product);

        assertEquals(product.getProductId(), saved.getProductId());
    }

    @Test
    void updateToProovedProduct() {
        String productId = "aa";

        ProductDto saved = productService.updateToProovedProduct(productId);

        log.info(saved.toString());
    }
}