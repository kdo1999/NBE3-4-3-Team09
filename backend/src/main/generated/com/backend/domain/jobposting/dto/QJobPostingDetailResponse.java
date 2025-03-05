package com.backend.domain.jobposting.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.backend.domain.jobposting.dto.QJobPostingDetailResponse is a Querydsl Projection type for JobPostingDetailResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QJobPostingDetailResponse extends ConstructorExpression<JobPostingDetailResponse> {

    private static final long serialVersionUID = -1286138868L;

    public QJobPostingDetailResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<String> url, com.querydsl.core.types.Expression<java.time.ZonedDateTime> postDate, com.querydsl.core.types.Expression<java.time.ZonedDateTime> openDate, com.querydsl.core.types.Expression<java.time.ZonedDateTime> closeDate, com.querydsl.core.types.Expression<String> companyName, com.querydsl.core.types.Expression<String> companyLink, com.querydsl.core.types.Expression<? extends com.backend.domain.jobposting.entity.ExperienceLevel> experienceLevel, com.querydsl.core.types.Expression<? extends com.backend.domain.jobposting.entity.RequireEducate> requireEducate, com.querydsl.core.types.Expression<com.backend.domain.jobposting.entity.JobPostingStatus> jobPostingStatus, com.querydsl.core.types.Expression<? extends com.backend.domain.jobposting.entity.Salary> salary, com.querydsl.core.types.Expression<? extends java.util.List<com.backend.domain.jobskill.dto.JobSkillResponse>> jobSkillList, com.querydsl.core.types.Expression<Long> applyCnt, com.querydsl.core.types.Expression<Long> voterCount, com.querydsl.core.types.Expression<Boolean> isVoter) {
        super(JobPostingDetailResponse.class, new Class<?>[]{long.class, String.class, String.class, java.time.ZonedDateTime.class, java.time.ZonedDateTime.class, java.time.ZonedDateTime.class, String.class, String.class, com.backend.domain.jobposting.entity.ExperienceLevel.class, com.backend.domain.jobposting.entity.RequireEducate.class, com.backend.domain.jobposting.entity.JobPostingStatus.class, com.backend.domain.jobposting.entity.Salary.class, java.util.List.class, long.class, long.class, boolean.class}, id, subject, url, postDate, openDate, closeDate, companyName, companyLink, experienceLevel, requireEducate, jobPostingStatus, salary, jobSkillList, applyCnt, voterCount, isVoter);
    }

}

