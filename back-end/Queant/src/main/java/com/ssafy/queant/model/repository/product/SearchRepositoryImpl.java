package com.ssafy.queant.model.repository.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.queant.model.entity.product.QConditions;
import com.ssafy.queant.model.entity.product.QJoinway;
import com.ssafy.queant.model.entity.product.QOptions;
import com.ssafy.queant.model.entity.product.QTraitSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.queant.model.entity.product.QProduct.product;

@RequiredArgsConstructor
@Repository
public class SearchRepositoryImpl implements SearchRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Tuple> searchSingle(Long amount, boolean isDeposit, Boolean isSimpleInterest, Boolean isFixed, Integer period, List<Integer> banks, List<String> joinway, List<String> conditions, List<String> traitSet, Pageable pageable) {
        QJoinway qJoinway = QJoinway.joinway;
        QConditions qConditions = QConditions.conditions;
        QTraitSet qTraitSet = QTraitSet.traitSet;
        QOptions qOptions = QOptions.options;

        BooleanBuilder builder = new BooleanBuilder();

        // 예금 vs 적금 -> 무조건 설정하게 됨
        builder.and(product.isDeposit.eq(isDeposit));

        // 금액 범위 설정
        builder.and(product.budgetMin.coalesce(Long.MIN_VALUE).loe(amount));
        builder.and(product.budgetMax.coalesce(Long.MAX_VALUE).goe(amount));

        if (isSimpleInterest != null) {// 단리 복리 설정값이 들어옴
            builder.and(product.productId.in(
                    JPAExpressions.select(qOptions.productId).from(qOptions).where(qOptions.rateType.eq(isSimpleInterest))
            ));
        }

        if (banks.size() > 0) {
            builder.and(product.bankId.in(banks));
        }

        if (joinway.size() > 0) {
            builder.and(
                    product.productId.in(
                            JPAExpressions.select(qJoinway.productId).from(qJoinway).where(qJoinway.scodeId.in(joinway))
                    ));
        }

        if (conditions.size() > 0) {
            builder.and(
                    product.productId.in(
                            JPAExpressions.select(qConditions.productId).from(qConditions).where(qConditions.scodeId.in(conditions))
                    ));
        }

        if (traitSet.size() > 0) {
            builder.and(
                    product.productId.in(
                            JPAExpressions.select(qTraitSet.productId).from(qTraitSet).where(qTraitSet.scodeId.in(traitSet))
                    ));
        }


        List<Tuple> results = queryFactory
                .select(product, qOptions.baseRate.as("rate")).distinct()
                .from(product)
                .join(qOptions).on(product.productId.eq(qOptions.productId), qOptions.saveTerm.eq(period))
                .where(builder)
                .orderBy(qOptions.baseRate.desc())
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }
}
