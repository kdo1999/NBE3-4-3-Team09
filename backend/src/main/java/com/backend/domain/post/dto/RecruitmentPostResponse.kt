package com.backend.domain.post.dto

import com.backend.domain.post.entity.RecruitmentStatus
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

data class RecruitmentPostResponse @QueryProjection constructor(
    val id: Long,
    var subject: String,
    var content: String,
    val categoryId: Long,
    val isAuthor: Boolean,
    val authorName: String,
    val authorImg: String,
    var voterCount: Long,
    var isVoter: Boolean,
    val createdAt: ZonedDateTime,

    @field:JsonInclude(value = Include.NON_NULL)
    var jobPostingId: Long,
    @field:JsonInclude(value = Include.NON_NULL)
    var numOfApplicants: Int,
    @field:JsonInclude(value = Include.NON_NULL)
    var recruitmentStatus: RecruitmentStatus,
    @field:JsonInclude(value = Include.NON_NULL)
    var currentAcceptedCount: Int

)
