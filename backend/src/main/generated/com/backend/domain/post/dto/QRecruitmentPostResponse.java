package com.backend.domain.post.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.backend.domain.post.dto.QRecruitmentPostResponse is a Querydsl Projection type for RecruitmentPostResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRecruitmentPostResponse extends ConstructorExpression<RecruitmentPostResponse> {

    private static final long serialVersionUID = 1081071971L;

    public QRecruitmentPostResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> categoryId, com.querydsl.core.types.Expression<Boolean> isAuthor, com.querydsl.core.types.Expression<String> authorName, com.querydsl.core.types.Expression<String> authorImg, com.querydsl.core.types.Expression<Long> voterCount, com.querydsl.core.types.Expression<Boolean> isVoter, com.querydsl.core.types.Expression<java.time.ZonedDateTime> createdAt, com.querydsl.core.types.Expression<Long> jobPostingId, com.querydsl.core.types.Expression<Integer> numOfApplicants, com.querydsl.core.types.Expression<com.backend.domain.post.entity.RecruitmentStatus> recruitmentStatus, com.querydsl.core.types.Expression<Integer> currentAcceptedCount) {
        super(RecruitmentPostResponse.class, new Class<?>[]{long.class, String.class, String.class, long.class, boolean.class, String.class, String.class, long.class, boolean.class, java.time.ZonedDateTime.class, long.class, int.class, com.backend.domain.post.entity.RecruitmentStatus.class, int.class}, id, subject, content, categoryId, isAuthor, authorName, authorImg, voterCount, isVoter, createdAt, jobPostingId, numOfApplicants, recruitmentStatus, currentAcceptedCount);
    }

}

