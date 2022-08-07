package com.ssafy.queant.model.repository.product;

import com.ssafy.queant.model.entity.product.Product;
import org.junit.jupiter.api.Test;
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
        List<Product> p = searchRepository.searchSingle(false, null, list, joinway, conditions, traitSet);
        log.info(String.valueOf(p.size()));
//        for (Product product : p
//        ) {
//            log.info(product.toString());
//        }
    }
}