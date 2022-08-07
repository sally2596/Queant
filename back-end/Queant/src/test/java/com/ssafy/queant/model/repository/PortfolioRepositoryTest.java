package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.portfolio.PortfolioCondition;
import com.ssafy.queant.model.entity.product.Conditions;
import com.ssafy.queant.model.entity.product.Options;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.product.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;


    @Test
    public void InsertPortfolio(){

        Product product = Product.builder().productId(4).build();
        Options option = Options.builder().optionId(2).build();
        List<PortfolioCondition> portfolioCondition= new ArrayList<>();

        Conditions condition = Conditions.builder().conditionId(6).build();

        Portfolio portfolio = Portfolio.builder()
                .email("string")
                .product(product)
                .portfolioNo(0)
                .amount(10000L)
                .startDate(new java.util.Date())
                .options(option)
                .build();

        portfolio.addPortfolioCondition(PortfolioCondition.builder().conditions(condition).portfolio(portfolio).build());

        portfolioRepository.save(portfolio);
        System.out.println("test");
    }

}