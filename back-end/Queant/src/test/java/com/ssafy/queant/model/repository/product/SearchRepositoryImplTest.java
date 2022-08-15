package com.ssafy.queant.model.repository.product;

import com.querydsl.core.Tuple;
import com.ssafy.queant.model.dto.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        conditions.add("B007");
//        conditions.add("B004");
//        conditions.add("B005");
        List<String> traitSet = new ArrayList<>();
//        traitSet.add("E001");
        List<String> banktype = new ArrayList<>();
        banktype.add("C001");
        int period = 6;
        List<Tuple> p = searchRepository.searchSingle(0l, false, false, false, period, list, banktype, joinway,
                conditions,
                traitSet);
        log.info(String.valueOf(p));
        int cnt = 0;
        for (Tuple product : p
        ) {
            ProductDto dto = modelMapper.map(product, ProductDto.class);
            //dto.setBaseRate(product.get(1, Float.class));
            log.info("[TUPLE]: " + product.toString());
            cnt++;
        }
        log.info(String.valueOf(cnt));
    }
}