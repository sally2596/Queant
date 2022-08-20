package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptions is a Querydsl query type for Options
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptions extends EntityPathBase<Options> {

    private static final long serialVersionUID = 845094842L;

    public static final QOptions options = new QOptions("options");

    public final NumberPath<Float> baseRate = createNumber("baseRate", Float.class);

    public final NumberPath<Float> highBaseRate = createNumber("highBaseRate", Float.class);

    public final NumberPath<Integer> optionId = createNumber("optionId", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final BooleanPath rateType = createBoolean("rateType");

    public final BooleanPath rsrvType = createBoolean("rsrvType");

    public final NumberPath<Integer> saveTerm = createNumber("saveTerm", Integer.class);

    public QOptions(String variable) {
        super(Options.class, forVariable(variable));
    }

    public QOptions(Path<? extends Options> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptions(PathMetadata metadata) {
        super(Options.class, metadata);
    }

}

