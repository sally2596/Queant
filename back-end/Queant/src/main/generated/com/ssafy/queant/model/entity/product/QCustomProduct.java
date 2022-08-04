package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomProduct is a Querydsl query type for CustomProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomProduct extends EntityPathBase<CustomProduct> {

    private static final long serialVersionUID = 128097370L;

    public static final QCustomProduct customProduct = new QCustomProduct("customProduct");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> amountRegular = createNumber("amountRegular", Long.class);

    public final NumberPath<Float> baseRate = createNumber("baseRate", Float.class);

    public final DatePath<java.sql.Date> endDate = createDate("endDate", java.sql.Date.class);

    public final StringPath etc = createString("etc");

    public final StringPath institutionName = createString("institutionName");

    public final BooleanPath isDeposit = createBoolean("isDeposit");

    public final BooleanPath isFixedRsrv = createBoolean("isFixedRsrv");

    public final ComparablePath<java.util.UUID> memberId = createComparable("memberId", java.util.UUID.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Float> specialRate = createNumber("specialRate", Float.class);

    public final DatePath<java.sql.Date> startDate = createDate("startDate", java.sql.Date.class);

    public QCustomProduct(String variable) {
        super(CustomProduct.class, forVariable(variable));
    }

    public QCustomProduct(Path<? extends CustomProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomProduct(PathMetadata metadata) {
        super(CustomProduct.class, metadata);
    }

}

