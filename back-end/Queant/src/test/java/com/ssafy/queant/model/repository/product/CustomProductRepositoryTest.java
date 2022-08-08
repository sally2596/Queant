package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.CustomProduct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomProductRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(CustomProductRepositoryTest.class);

    @Autowired
    private CustomProductRepository customProductRepository;

    @Test
    public void 사용자정의상품_단품_저장() {
        //given
        final CustomProduct product = CustomProduct.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(UUID.randomUUID())
                .build();

        //when
        final CustomProduct savedProduct = customProductRepository.save(product);

        //then
        assertEquals(product.getProductId(), savedProduct.getProductId());
        log.info("***************************" + product.getProductId());
    }

    @Test
    public void 사용자정의상품_단품_삭제() throws Exception {
        // given
        final CustomProduct product = CustomProduct.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(UUID.randomUUID())
                .build();

        final CustomProduct savedProduct = customProductRepository.save(product);

        // when
        customProductRepository.delete(savedProduct);
        // then
    }

    @Test
    public void 사용자정의상품_단품_찾기() throws Exception {
        // given
        final CustomProduct product = CustomProduct.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(UUID.randomUUID())
                .build();

        final CustomProduct savedProduct = customProductRepository.save(product);

        // when
        final Optional<CustomProduct> findProduct = customProductRepository.findByProductId(savedProduct.getProductId());

        int actual = findProduct.get().getProductId();
        int expected = savedProduct.getProductId();

        // then
        assertEquals(actual, expected);
    }

    @Test
    public void 사용자이메일로_사용자정의예금상품_찾기() {
        //given
        final UUID uuid = UUID.randomUUID();

        final CustomProduct product1 = CustomProduct.builder()
                .institutionName("우리은행")
                .productName("1111")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(uuid)
                .build();

        final CustomProduct product2 = CustomProduct.builder()
                .institutionName("국민은행")
                .productName("2222")
                .isDeposit(true)
                .baseRate(10.4f)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(uuid)
                .build();

        final CustomProduct product3 = CustomProduct.builder()
                .institutionName("신한은행")
                .productName("3333")
                .isDeposit(true)
                .baseRate(10.4f)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .memberId(UUID.randomUUID())
                .build();


        final CustomProduct savedProduct1 = customProductRepository.save(product1);
        final CustomProduct savedProduct2 = customProductRepository.save(product2);
        final CustomProduct savedProduct3 = customProductRepository.save(product3);

        //when
//        List<CustomProduct> list = customProductRepository.findByMemberId(uuid);
//
//        //then
//        for (int i = 0; i < list.size(); i++) {
//            log.info("["+i+"번째 커스텀 상품]");
//            log.info(list.get(i).toString());
//        }
    }

}