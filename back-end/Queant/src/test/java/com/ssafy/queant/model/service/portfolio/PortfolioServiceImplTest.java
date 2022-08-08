package com.ssafy.queant.model.service.portfolio;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PortfolioServiceImplTest {

    @Autowired PortfolioService portfolioService;

    @Test
    void insertPortfolio() {
        PortfolioDto portfolioDto= PortfolioDto.builder()
                .portfolioNo(2)
                .amount(10000L)
                .startDate(new Date())
                .build();
//        PortfolioDto result = portfolioService.insertPortfolio("string",  portfolioDto,"01012000200000000003");
    }
}