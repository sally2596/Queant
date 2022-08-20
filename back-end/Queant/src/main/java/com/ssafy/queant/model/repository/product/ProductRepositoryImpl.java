package com.ssafy.queant.model.repository.product;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.queant.model.entity.product.QOptions.options;
import static com.ssafy.queant.model.entity.product.QProduct.product;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ProductRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public List<Tuple> findByIsEnabledTrueAndNameContaining(String name) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.isEnabled.eq(true));
        builder.and(product.name.contains(name));

        List<Tuple> list = queryFactory
                .select(product, options.baseRate.min())
                .from(product)
                .where(builder)
                .leftJoin(options).on(product.productId.eq(options.productId))
                .groupBy(product.productId)
                .fetch();
        return list;
    }

    public List<Tuple> findByBankIdAndIsEnabledTrue(int bankId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.isEnabled.eq(true));
        builder.and(product.bankId.eq(bankId));

        List<Tuple> list = queryFactory
                .select(product, options.baseRate.min())
                .from(product)
                .where(builder)
                .leftJoin(options).on(product.productId.eq(options.productId))
                .groupBy(product.productId)
                .fetch();
        return list;
    }

}
