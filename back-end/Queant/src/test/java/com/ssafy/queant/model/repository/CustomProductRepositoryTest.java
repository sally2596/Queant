package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.member.Member;
import com.ssafy.queant.model.entity.product.CustomProduct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomProductRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(CustomProductRepositoryTest.class);

    @Autowired
    private CustomProductRepository customProductRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 사용자정의예금상품저장(){
        //given
        final CustomProduct product = CustomProduct.builder()
                .institution_name("우리은행")
                .product_name("test1")
                .is_deposit(true)
                .base_rate(1.5)
                .start_date(new Date(System.currentTimeMillis()))
                .end_date(new Date(System.currentTimeMillis()+1000))
                .member_id(UUID.randomUUID())
                .build();

        //when
        final CustomProduct savedProduct = customProductRepository.save(product);

        //then
        assertEquals(product.getProduct_id(),savedProduct.getProduct_id());
        log.info("***************************"+product.getProduct_id());
    }

    @Test
    public void 사용자이메일로_사용자정의예금상품_찾기(){
        //given
        final Member member = Member.builder()
                .email("luxurysally@naver.com")
                .name("전선영")
                .build();

        final Member savedMember = memberRepository.save(member);

        final UUID uuid = savedMember.getMember_id();

        final CustomProduct product1= CustomProduct.builder()
                .institution_name("우리은행")
                .product_name("1111")
                .is_deposit(true)
                .base_rate(1.5)
                .start_date(new Date(System.currentTimeMillis()))
                .end_date(new Date(System.currentTimeMillis()+1000))
                .member_id(uuid)
                .build();

        final CustomProduct product2 = CustomProduct.builder()
                .institution_name("국민은행")
                .product_name("2222")
                .is_deposit(true)
                .base_rate(10.4)
                .start_date(new Date(System.currentTimeMillis()))
                .end_date(new Date(System.currentTimeMillis()+1000))
                .member_id(uuid)
                .build();

        final CustomProduct product3 = CustomProduct.builder()
                .institution_name("신한은행")
                .product_name("3333")
                .is_deposit(true)
                .base_rate(10.4)
                .start_date(new Date(System.currentTimeMillis()))
                .end_date(new Date(System.currentTimeMillis()+1000))
                .member_id(UUID.randomUUID())
                .build();


        final CustomProduct savedProduct1 = customProductRepository.save(product1);
        final CustomProduct savedProduct2 = customProductRepository.save(product2);
        final CustomProduct savedProduct3 = customProductRepository.save(product3);

        //when
        List<CustomProduct> list = customProductRepository.findByMemberEmail("luxurysally@naver.com");

        //then
        for (int i = 0; i < list.size(); i++) {
            log.info("["+i+"번째 커스텀 상품]");
            log.info(list.get(i).toString());
        }
    }

}