package com.ssafy.queant.model.entity.portfolio;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortfolio is a Querydsl query type for Portfolio
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPortfolio extends EntityPathBase<Portfolio> {

    private static final long serialVersionUID = -344395331L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortfolio portfolio = new QPortfolio("portfolio");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final SetPath<com.ssafy.queant.model.entity.product.Conditions, com.ssafy.queant.model.entity.product.QConditions> conditions = this.<com.ssafy.queant.model.entity.product.Conditions, com.ssafy.queant.model.entity.product.QConditions>createSet("conditions", com.ssafy.queant.model.entity.product.Conditions.class, com.ssafy.queant.model.entity.product.QConditions.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final com.ssafy.queant.model.entity.member.QMember member;

    public final com.ssafy.queant.model.entity.product.QOptions option;

    public final NumberPath<Integer> portfolioId = createNumber("portfolioId", Integer.class);

    public final NumberPath<Integer> portfolioNo = createNumber("portfolioNo", Integer.class);

    public final com.ssafy.queant.model.entity.product.QProduct product;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public QPortfolio(String variable) {
        this(Portfolio.class, forVariable(variable), INITS);
    }

    public QPortfolio(Path<? extends Portfolio> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortfolio(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortfolio(PathMetadata metadata, PathInits inits) {
        this(Portfolio.class, metadata, inits);
    }

    public QPortfolio(Class<? extends Portfolio> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssafy.queant.model.entity.member.QMember(forProperty("member")) : null;
        this.option = inits.isInitialized("option") ? new com.ssafy.queant.model.entity.product.QOptions(forProperty("option")) : null;
        this.product = inits.isInitialized("product") ? new com.ssafy.queant.model.entity.product.QProduct(forProperty("product")) : null;
    }

}

