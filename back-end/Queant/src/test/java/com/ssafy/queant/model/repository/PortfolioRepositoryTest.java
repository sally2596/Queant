package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.dto.portfolio.PortfolioDto;
import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.portfolio.Portfolio;
import com.ssafy.queant.model.repository.portfolio.PortfolioRepository;
import com.ssafy.queant.model.repository.product.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;


//    @Test
//    public void InsertPortfolio(){
//
//        Product product = Product.builder().productId(4).build();
//        Options option = Options.builder().optionId(2).build();
//        Member member = Member.builder().memberId(UUID.fromString("40650130-d043-4d15-84dd-eeb6e25e8bd0")).build();
//        List<PortfolioCondition> portfolioCondition= new ArrayList<>();
//
//        Conditions condition = Conditions.builder().conditionId(6).build();
//
//        Portfolio portfolio = Portfolio.builder()
//                .member(member)
//                .product(product)
//                .portfolioNo(0)
//                .amount(10000L)
//                .startDate(new java.util.Date())
//                .option(option)
//                .build();
//
//        portfolio.addCondition(condition);
//
//        portfolioRepository.save(portfolio);
//        System.out.println("test");
//    }

    @Test
    @Transactional
    public void getPortfolio() {
        Member member = Member.builder().memberId(UUID.fromString("40650130-d043-4d15-84dd-eeb6e25e8bd0")).build();
        Optional<List<Portfolio>> portfolios = portfolioRepository.findPortfolioByMemberAndPortfolioNo(member, 0);


        System.out.println(portfolios.get().size());
        for (Portfolio portfolio : portfolios.get()) {
            PortfolioDto portfolioDto = modelMapper.map(portfolio, PortfolioDto.class);
            System.out.println(portfolioDto.toString());

        }
    }

}