package com.ssafy.queant.model.repository.product;

import com.querydsl.core.Tuple;
import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SearchRepositoryImplTest {

    private final Logger log = LoggerFactory.getLogger(SearchRepositoryImplTest.class);

    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void searchBank() {
        List<Integer> list = new ArrayList<>();
//        list.add(10001);
//        list.add(10002);
        List<String> joinway = new ArrayList<>();
//        joinway.add("A001");
        List<String> conditions = new ArrayList<>();
//        conditions.add("B003");
        List<String> traitSet = new ArrayList<>();
//        traitSet.add("E001");

        int period = 12;
        Pageable pageable = PageRequest.of(0, 50);
        Page<Tuple> p = searchRepository.searchSingle(false, null, false, period, list, joinway, conditions, traitSet, pageable);
        log.info(String.valueOf(p));
        for (Tuple product : p
        ) {
            ProductDto dto = modelMapper.map(product, ProductDto.class);
            dto.setBaseRate(product.get(1, Float.class));
            log.info("[TUPLE]: " + product.toString());
        }
        log.info(String.valueOf(p.getSize()));
    }
}