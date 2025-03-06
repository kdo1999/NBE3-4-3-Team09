package com.backend.domain.jobposting.dto

import com.backend.domain.jobposting.entity.ExperienceLevel
import com.backend.domain.jobposting.entity.JobPostingStatus
import com.backend.domain.jobposting.entity.RequireEducate
import com.backend.domain.jobposting.entity.Salary
import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

/**
 * JobPostingPageResponse
 * <p>페이징 조회시 응답할 객체 입니다.</p>
 *
 * @param id               id
 * @param subject          제목
 * @param openDate         공개 날짜
 * @param closeDate        마감 날짜
 * @param experienceLevel  직무 경력
 * @param requireEducate   학력
 * @param jobPostingStatus 공고 상태
 * @param salary           연봉
 * @param applyCnt         지원자 수
 * @author Kim Dong O
 */
data class JobPostingPageResponse @QueryProjection constructor(
    val id: Long,
    val subject: String, // 제목

    val openDate: ZonedDateTime, // 공개 날짜
    val closeDate: ZonedDateTime, // 마감 날짜

    val experienceLevel: ExperienceLevel, // 직무 경력

    val requireEducate: RequireEducate, // 학력

    val jobPostingStatus: JobPostingStatus, // 공고 상태

    val salary: Salary, // 연봉

    val applyCnt: Long // 지원자 수
)
