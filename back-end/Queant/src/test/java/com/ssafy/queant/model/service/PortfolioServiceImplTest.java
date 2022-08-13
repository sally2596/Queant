package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.product.CustomProductDto;
import com.ssafy.queant.model.repository.MemberRepository;
import com.ssafy.queant.model.service.portfolio.PortfolioServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PortfolioServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(PortfolioServiceImplTest.class);

    @Autowired
    private PortfolioServiceImpl portfolioService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 사용자정의상품_저장() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();

        final CustomProductDto product = CustomProductDto.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .build();

        // when
        CustomProductDto actual = portfolioService.registCustomProduct(product, uuid);

        // then
        log.info(actual.toString());
    }

    @Test
    public void 사용자정의상품_삭제() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();

        final CustomProductDto product = CustomProductDto.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000))
                .build();


        CustomProductDto saved = portfolioService.registCustomProduct(product, uuid);

        // when
        boolean actual = portfolioService.deleteCustomProduct(saved);

        // then
        assertEquals(actual, true);
    }

    @Test
    public void deletePortfolio() throws Exception {
        portfolioService.deletePortfolioSingle(2);
    }
}