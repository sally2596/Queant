package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.product.Bank;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
}