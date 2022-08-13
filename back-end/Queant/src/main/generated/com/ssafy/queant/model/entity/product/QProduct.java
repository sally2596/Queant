package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1785095691L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> ageMax = createNumber("ageMax", Integer.class);

    public final NumberPath<Integer> ageMin = createNumber("ageMin", Integer.class);

    public final NumberPath<Integer> bankId = createNumber("bankId", Integer.class);

    public final NumberPath<Long> budgetMax = createNumber("budgetMax", Long.class);

    public final NumberPath<Long> budgetMin = createNumber("budgetMin", Long.class);

    public final StringPath etc = createString("etc");

    public final BooleanPath isDeposit = createBoolean("isDeposit");

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final StringPath name = createString("name");

    public final StringPath picture = createString("picture");

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath scodeId = createString("scodeId");

    public final NumberPath<Integer> termMax = createNumber("termMax", Integer.class);

    public final NumberPath<Integer> termMin = createNumber("termMin", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

