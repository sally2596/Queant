package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportProduct is a Querydsl query type for ReportProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportProduct extends EntityPathBase<ReportProduct> {

    private static final long serialVersionUID = -1777475273L;

    public static final QReportProduct reportProduct = new QReportProduct("reportProduct");

    public final StringPath bankName = createString("bankName");

    public final StringPath etc = createString("etc");

    public final BooleanPath isDeposit = createBoolean("isDeposit");

    public final BooleanPath isUpdated = createBoolean("isUpdated");

    public final ComparablePath<java.util.UUID> member_id = createComparable("member_id", java.util.UUID.class);

    public final StringPath productName = createString("productName");

    public final StringPath referenceData = createString("referenceData");

    public final NumberPath<Integer> reportProductId = createNumber("reportProductId", Integer.class);

    public QReportProduct(String variable) {
        super(ReportProduct.class, forVariable(variable));
    }

    public QReportProduct(Path<? extends ReportProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportProduct(PathMetadata metadata) {
        super(ReportProduct.class, metadata);
    }

}

