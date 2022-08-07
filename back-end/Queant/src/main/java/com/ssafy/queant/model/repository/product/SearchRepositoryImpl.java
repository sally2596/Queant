package com.ssafy.queant.model.repository.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.queant.model.entity.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.queant.model.entity.product.QProduct.product;

@RequiredArgsConstructor
@Repository
public class SearchRepositoryImpl implements SearchRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Product> searchSingle(boolean isDeposit, Boolean isSimpleInterest, int period, List<Integer> banks, List<String> joinway, List<String> conditions, List<String> traitSet) {
        QJoinway qJoinway = QJoinway.joinway;
        QConditions qConditions = QConditions.conditions;
        QTraitSet qTraitSet = QTraitSet.traitSet;
        QOptions qOptions = QOptions.options;

        BooleanBuilder builder = new BooleanBuilder();

        // 예금 vs 적금 -> 무조건 설정하게 됨
        builder.and(product.isDeposit.eq(isDeposit));

        // 예치 기간
        builder.and(product.termMin.loe(period));
        builder.and(product.termMax.goe(period));

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


        List<Product> results = queryFactory
                .selectFrom(product).distinct()
                .where(builder)
                .fetch();
        return results;
    }
}
