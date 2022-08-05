package com.ssafy.queant.model.entity.content;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContent extends EntityPathBase<Content> {

    private static final long serialVersionUID = 1028000543L;

    public static final QContent content1 = new QContent("content1");

    public final StringPath content = createString("content");

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath title = createString("title");

    public QContent(String variable) {
        super(Content.class, forVariable(variable));
    }

    public QContent(Path<? extends Content> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContent(PathMetadata metadata) {
        super(Content.class, metadata);
    }

}

