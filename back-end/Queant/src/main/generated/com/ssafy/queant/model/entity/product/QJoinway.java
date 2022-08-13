package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QJoinway is a Querydsl query type for Joinway
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJoinway extends EntityPathBase<Joinway> {

    private static final long serialVersionUID = 663912097L;

    public static final QJoinway joinway = new QJoinway("joinway");

    public final NumberPath<Integer> joinwayId = createNumber("joinwayId", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath scodeId = createString("scodeId");

    public QJoinway(String variable) {
        super(Joinway.class, forVariable(variable));
    }

    public QJoinway(Path<? extends Joinway> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJoinway(PathMetadata metadata) {
        super(Joinway.class, metadata);
    }

}

