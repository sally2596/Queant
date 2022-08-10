package com.ssafy.queant.model.service;

import com.ssafy.queant.model.dto.Search.SearchKeywordDto;
import com.ssafy.queant.model.dto.Search.SearchRequestDto;
import com.ssafy.queant.model.dto.product.ProductDto;
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
        List<Integer> list = new ArrayList<>();
//        list.add(10001);

        List<String> conditions = new ArrayList<>();
        conditions.add("B003");
        conditions.add("B004");
        conditions.add("B005");
        SearchRequestDto searchRequestDto = SearchRequestDto.builder()
                .isSimpleInterest(false)
                .isFixed(false)
                .bank(list)
                .bankType(new ArrayList<>())
                .conditions(conditions)
                .joinway(new ArrayList<>())
                .traitSet(new ArrayList<>())
                .period(12)
                .amount(0l)
                .build();

        List<ProductDto> searchResponseDto = searchService.searchSingle(searchRequestDto, false);
        int cnt = 0;
        for (ProductDto product : searchResponseDto
        ) {
            log.info("[TUPLE]: " + product.getProductId() + "  금리: " + product.getTotalRate());
            cnt++;
        }
        log.info(String.valueOf(cnt));
    }
}