package com.ssafy.queant.model.service.product;

import com.ssafy.queant.model.dto.product.*;
import com.ssafy.queant.model.repository.product.ReportProductRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ProductServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ReportProductRepository reportProductRepository;


    @Test
    void findByNameContaining() {
        String name = "OK정기예금";

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

    @Test
    void updateProduct() {
        //제품 데이터 추가용 데이터 추가
        ProductDto productDto = new ProductDto();
        productDto.setName("상품이름");
        productDto.setDeposit(false);
        productDto.setEnabled(true);
        productDto.setScodeId("B101");

        List<OptionsDto> options = new ArrayList<OptionsDto>();
        OptionsDto option = new OptionsDto();
        option.setBaseRate(1.0f);
        option.setSaveTerm(12);
        option.setRsrvType(false);
        options.add(option);

        List<ConditionsDto> conditions = new ArrayList<ConditionsDto>();
        ConditionsDto condition = new ConditionsDto();
        condition.setScodeId("B101");
        condition.setSpecialRate(1.0f);
        conditions.add(condition);

        List<JoinwayDto> joins = new ArrayList<JoinwayDto>();
        JoinwayDto join = new JoinwayDto();
        join.setScodeId("B101");
        joins.add(join);

        ProductDetailDto test = new ProductDetailDto();
        test.setProduct(productDto);
        test.setOptions(options);
        test.setConditions(conditions);
        test.setJoinway(joins);

        productService.updateReportToProduct(2, test);

    }

}