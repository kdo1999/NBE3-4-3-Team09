package com.backend.domain.post.repository.recruitment

import com.backend.domain.post.entity.RecruitmentPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime

interface RecruitmentPostJpaRepository : JpaRepository<RecruitmentPost, Long> {
    @Query("SELECT rp\n FROM RecruitmentPost rp LEFT JOIN FETCH rp.author LEFT JOIN FETCH rp.jobPosting\n WHERE rp.postId = :postId")
    fun findByIdFetch(@Param("postId") postId: Long): RecruitmentPost?

    @Query("""
        SELECT rq
        FROM RecruitmentPost rq
        LEFT JOIN FETCH rq.author
        WHERE rq.recruitmentClosingDate < :recruitmentClosingDate
        AND rq.recruitmentStatus = 'OPEN'
    """)
    fun findPostByRecruitmentClosingDateAndRecruitmentStatus(
        @Param("recruitmentClosingDate") recruitmentClosingDate: ZonedDateTime
        ): List<RecruitmentPost>

    @Query(nativeQuery = true,
        value = """
        UPDATE recruitment_post rq
        SET rq.recruitment_status = 'CLOSED'
        WHERE rq.recruitment_closing_date < :recruitmentClosingDate
        AND rq.recruitment_status = 'OPEN'
        AND rq.post_id IN(:postId)
        """)
    @Modifying
    fun updatePostByRecruitmentClosingDate(
        @Param("recruitmentClosingDate") recruitmentClosingDate: ZonedDateTime,
        @Param("postId") postIdList: List<Long>
    )
}