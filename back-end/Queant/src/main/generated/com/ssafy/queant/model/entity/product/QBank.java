package com.ssafy.queant.model.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBank is a Querydsl query type for Bank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBank extends EntityPathBase<Bank> {

    private static final long serialVersionUID = 1140155040L;

    public static final QBank bank = new QBank("bank");

    public final NumberPath<Integer> bankId = createNumber("bankId", Integer.class);

    public final StringPath bankName = createString("bankName");

    public final StringPath homepage = createString("homepage");

    public final StringPath picture = createString("picture");

    public final StringPath scodeId = createString("scodeId");

    public final StringPath tel = createString("tel");

    public QBank(String variable) {
        super(Bank.class, forVariable(variable));
    }

    public QBank(Path<? extends Bank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBank(PathMetadata metadata) {
        super(Bank.class, metadata);
    }

}

