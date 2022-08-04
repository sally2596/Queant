package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.entity.product.Product;
import com.ssafy.queant.model.repository.product.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
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
        Optional<Member> result = memberRepository.findByEmail("string");
        Member member = result.get();
        System.out.println(member.toString());
        Product product = productRepository.findByProductId("01012000200000000003");
        Portfolio portfolio = Portfolio.builder()
                .member(member)
                .product(product)
                .portfolioNo(0)
                .amount(10000L)
                .startDate(new java.util.Date())
                .build();

        portfolioRepository.save(portfolio);
        System.out.println("test");
    }

}