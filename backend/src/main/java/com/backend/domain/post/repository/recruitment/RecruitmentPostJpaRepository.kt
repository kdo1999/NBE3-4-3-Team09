package com.backend.domain.post.repository.recruitment

import com.backend.domain.post.entity.RecruitmentPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RecruitmentPostJpaRepository : JpaRepository<RecruitmentPost, Long> {
    @Query("SELECT rp\n FROM RecruitmentPost rp LEFT JOIN FETCH rp.author LEFT JOIN FETCH rp.jobPosting\n WHERE rp.postId = :postId")
    fun findByIdFetch(@Param("postId") postId: Long): RecruitmentPost?
}