package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.BankKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.product.SearchResponseDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void searchSingle() {
        List<BankKeywordDto> list = new ArrayList<>();
        list.add(BankKeywordDto.builder()
                .bankId(10001)
                .scodeId("C001")
                .bankName("우리은행")
                .shortName("우리은행")
                .build());
        SearchRequestDto searchRequestDto = SearchRequestDto.builder()
                .isSimpleInterest(null)
                .isFixed(false)
                .bank(list)
                .bankType(new ArrayList<>())
                .conditions(new ArrayList<>())
                .joinway(new ArrayList<>())
                .traitSet(new ArrayList<>())
                .period(12)
                .amount(0l)
                .build();

        SearchResponseDto searchResponseDto = searchService.searchSingle(searchRequestDto, false, 1);
        log.info(searchResponseDto.getProductDtoList().toString());
        log.info("[totalCount]: " + String.valueOf(searchResponseDto.getTotalCount()));
        log.info("[totalPage]: " + searchResponseDto.getTotalPage());
    }
}