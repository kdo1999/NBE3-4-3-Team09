package com.backend.domain.post.repository.recruitment

import com.backend.domain.post.dto.RecruitmentPostResponse
import com.backend.domain.post.entity.RecruitmentPost
import java.time.ZonedDateTime

interface RecruitmentPostRepository {

    fun findById(id: Long) : RecruitmentPost?

    fun findByIdFetch(id: Long) : RecruitmentPost?

    fun save(recruitmentPost: RecruitmentPost) : RecruitmentPost

    fun deleteById(id: Long)

    fun findAll(): List<RecruitmentPost>

    fun findPostResponseById(postId: Long, siteUserId: Long) : RecruitmentPostResponse?

    fun findPostByRecruitmentClosingDateAndRecruitmentStatus(
        recruitmentClosingDate: ZonedDateTime
    ): List<RecruitmentPost>

    fun updatePostByRecruitmentClosingDate(
        recruitmentClosingDate: ZonedDateTime,
        postIdList: List<Long>
    )
}