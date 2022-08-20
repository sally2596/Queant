package com.ssafy.queant.model.repository;

import com.ssafy.queant.model.entity.SpecificCode;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpecificCodeRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(SpecificCodeRepositoryTest.class);

    @Autowired
    private SpecificCodeRepository specificCodeRepository;

    @Test
    void findByScodeId() {
        String scodeId = "A001";
        Optional<SpecificCode> result = specificCodeRepository.findByScodeId(scodeId);

        SpecificCode specificCode = result.get();
        String actual = specificCode.getScodeId();
        String expected = scodeId;

        // then
        assertEquals(actual,expected);
    }

    @Test
    void findByCodeId() {
        String codeId = "A";
        List<SpecificCode> result = specificCodeRepository.findByCodeId(codeId);

        for (SpecificCode s:result) {
            log.info(s.toString());
        }
    }
}
