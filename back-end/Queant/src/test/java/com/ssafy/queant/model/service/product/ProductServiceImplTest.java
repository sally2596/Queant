package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.ProductDetailDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
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
        int productId = 1;
        ProductDetailDto productDetailDto = productService.findByProductId(productId);
        if (productDetailDto == null) log.info("null값 들어옴");
        else log.info(productDetailDto.toString());
    }

    @Test
    void registProduct() {
        ProductDto product = ProductDto.builder()
                .productCode("aa")
                .bankId(10345)
                .name("약오르지 까꿍")
                .scodeId("A001")
                .isDeposit(true)
                .isEnabled(false)
                .build();

        ProductDto saved = productService.registProduct(product);
    }

//    @Test
//    void updateToProovedProduct() {
//        int productId = 1;
//
//        ProductDto saved = productService.updateToProovedProduct(productId);
//
//        log.info(saved.toString());
//    }

    @Test
    void findByIsEnabledFalse() {
        List<ProductDto> list = productService.findByIsEnabledFalse();
        for (ProductDto p : list
        ) {
            log.info(p.toString());
        }
    }
}