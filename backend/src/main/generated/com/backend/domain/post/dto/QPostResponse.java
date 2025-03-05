package com.backend.domain.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.backend.domain.post.dto.QPostResponse is a Querydsl Projection type for PostResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostResponse extends ConstructorExpression<PostResponse> {

    private static final long serialVersionUID = 2033486875L;

    public QPostResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> categoryId, com.querydsl.core.types.Expression<Boolean> isAuthor, com.querydsl.core.types.Expression<String> authorName, com.querydsl.core.types.Expression<String> authorImg, com.querydsl.core.types.Expression<Long> voterCount, com.querydsl.core.types.Expression<Boolean> isVoter, com.querydsl.core.types.Expression<java.time.ZonedDateTime> createdAt) {
        super(PostResponse.class, new Class<?>[]{long.class, String.class, String.class, long.class, boolean.class, String.class, String.class, long.class, boolean.class, java.time.ZonedDateTime.class}, id, subject, content, categoryId, isAuthor, authorName, authorImg, voterCount, isVoter, createdAt);
    }

}

