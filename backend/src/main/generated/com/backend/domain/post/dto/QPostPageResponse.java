package com.backend.domain.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.backend.domain.post.dto.QPostPageResponse is a Querydsl Projection type for PostPageResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostPageResponse extends ConstructorExpression<PostPageResponse> {

    private static final long serialVersionUID = -1603807542L;

    public QPostPageResponse(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<String> categoryName, com.querydsl.core.types.Expression<String> authorName, com.querydsl.core.types.Expression<String> authorProfileImage, com.querydsl.core.types.Expression<Long> commentCount, com.querydsl.core.types.Expression<Long> voterCount, com.querydsl.core.types.Expression<java.time.ZonedDateTime> createdAt) {
        super(PostPageResponse.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, long.class, long.class, java.time.ZonedDateTime.class}, postId, subject, categoryName, authorName, authorProfileImage, commentCount, voterCount, createdAt);
    }

}

