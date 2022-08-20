package com.ssafy.queant.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpecificCode is a Querydsl query type for SpecificCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpecificCode extends EntityPathBase<SpecificCode> {

    private static final long serialVersionUID = -868993788L;

    public static final QSpecificCode specificCode = new QSpecificCode("specificCode");

    public final StringPath codeId = createString("codeId");

    public final StringPath scodeId = createString("scodeId");

    public final StringPath scodeValue = createString("scodeValue");

    public QSpecificCode(String variable) {
        super(SpecificCode.class, forVariable(variable));
    }

    public QSpecificCode(Path<? extends SpecificCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpecificCode(PathMetadata metadata) {
        super(SpecificCode.class, metadata);
    }

}

