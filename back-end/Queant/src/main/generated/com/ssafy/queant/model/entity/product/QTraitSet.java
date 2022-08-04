package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTraitSet is a Querydsl query type for TraitSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTraitSet extends EntityPathBase<TraitSet> {

    private static final long serialVersionUID = 1783429592L;

    public static final QTraitSet traitSet = new QTraitSet("traitSet");

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath scodeId = createString("scodeId");

    public final NumberPath<Integer> traitId = createNumber("traitId", Integer.class);

    public QTraitSet(String variable) {
        super(TraitSet.class, forVariable(variable));
    }

    public QTraitSet(Path<? extends TraitSet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTraitSet(PathMetadata metadata) {
        super(TraitSet.class, metadata);
    }

}

