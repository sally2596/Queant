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
@Transactional
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

<<<<<<< HEAD
//    @Test
//    public void 사용자정의상품_이메일로_찾기() throws Exception {
//        // given
//
//        final UUID uuid = UUID.randomUUID();
//        final UUID uuid2 = UUID.randomUUID();
//
//        final CustomProductDto product1 = CustomProductDto.builder()
//                .institutionName("우리은행")
//                .productName("test1")
//                .isDeposit(true)
//                .baseRate(1.5F)
//                .startDate(new Date(System.currentTimeMillis()))
//                .endDate(new Date(System.currentTimeMillis()+1000))
//                .build();
//
//        final CustomProductDto product2 = CustomProductDto.builder()
//                .institutionName("신한은행")
//                .productName("test2")
//                .isDeposit(true)
//                .baseRate(55.5F)
//                .startDate(new Date(System.currentTimeMillis()))
//                .endDate(new Date(System.currentTimeMillis()+1000))
//                .build();
//
//        final CustomProductDto product3 = CustomProductDto.builder()
//                .institutionName("국민은행")
//                .productName("test3")
//                .isDeposit(true)
//                .baseRate(3.3F)
//                .startDate(new Date(System.currentTimeMillis()))
//                .endDate(new Date(System.currentTimeMillis()+1000))
//                .memberId(uuid2)
//                .build();
//
//
//        final CustomProductDto savedProduct1 = portfolioService.registCustomProduct(product1,uuid);
//        final CustomProductDto savedProduct2 = portfolioService.registCustomProduct(product2,uuid);
//        final CustomProductDto savedProduct3 = portfolioService.registCustomProduct(product3,uuid2);
//
//        // when
//        List<CustomProductDto> list = portfolioService.findCustomProductByMemberId(uuid);
//
//        // then
//        for (int i = 0; i < list.size(); i++) {
//            log.info("******"+i);
//            log.info(list.get(i).toString());
//        }
//    }
=======
    @Test
    public void 사용자정의상품_이메일로_찾기() throws Exception {
        // given

        final UUID uuid = UUID.randomUUID();
        final UUID uuid2 = UUID.randomUUID();

        final CustomProductDto product1 = CustomProductDto.builder()
                .institutionName("우리은행")
                .productName("test1")
                .isDeposit(true)
                .baseRate(1.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()+1000))
                .build();

        final CustomProductDto product2 = CustomProductDto.builder()
                .institutionName("신한은행")
                .productName("test2")
                .isDeposit(true)
                .baseRate(55.5F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()+1000))
                .build();

        final CustomProductDto product3 = CustomProductDto.builder()
                .institutionName("국민은행")
                .productName("test3")
                .isDeposit(true)
                .baseRate(3.3F)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()+1000))
                //.memberId(uuid2)
                .build();


        final CustomProductDto savedProduct1 = portfolioService.registCustomProduct(product1,uuid);
        final CustomProductDto savedProduct2 = portfolioService.registCustomProduct(product2,uuid);
        final CustomProductDto savedProduct3 = portfolioService.registCustomProduct(product3,uuid2);

        // when
        List<CustomProductDto> list = portfolioService.findCustomProductByMemberId(uuid);

        // then
        for (int i = 0; i < list.size(); i++) {
            log.info("******"+i);
            log.info(list.get(i).toString());
        }
    }
>>>>>>> Back-end


}