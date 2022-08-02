package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SearchServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(SearchServiceImplTest.class);

    @Autowired
    private SearchServiceImpl searchService;

    @Test
    void getSearchKeyword() {
        SearchKeywordDto dto = searchService.getSearchKeyword();

        log.info(String.valueOf(dto));
    }
}