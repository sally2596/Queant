package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConditions is a Querydsl query type for Conditions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConditions extends EntityPathBase<Conditions> {

    private static final long serialVersionUID = 1111865372L;

    public static final QConditions conditions = new QConditions("conditions");

    public final NumberPath<Integer> conditionId = createNumber("conditionId", Integer.class);

    public final StringPath conditionInfo = createString("conditionInfo");

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath scodeId = createString("scodeId");

    public final NumberPath<Float> specialRate = createNumber("specialRate", Float.class);

    public QConditions(String variable) {
        super(Conditions.class, forVariable(variable));
    }

    public QConditions(Path<? extends Conditions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConditions(PathMetadata metadata) {
        super(Conditions.class, metadata);
    }

}

