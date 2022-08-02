package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.Bank;
import com.ssafy.queant.model.repository.product.BankRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BankRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(BankRepositoryTest.class);

    @Autowired
    private BankRepository bankRepository;

    @Test
    void findAll() {
        List<Bank> list = bankRepository.findAll();

        for (Bank b:list
             ) {
            log.info(b.toString());
        }
    }

    @Test
    void findById(){
        // given
        int bankId = 10345;

        // when
        Optional<Bank> result = bankRepository.findByBankId(bankId);
        Bank bank = result.get();

        //then
        int actual = bankId;
        int expected = bank.getBankId();

        assertEquals(actual,expected);
    }
}