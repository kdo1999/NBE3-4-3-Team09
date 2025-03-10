package com.backend.domain.recruitmentUser.repository

import com.backend.domain.recruitmentUser.entity.RecruitmentUser
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

/**
 * 모집 지원자 관련 데이터 처리 리포지토리
 */
interface RecruitmentUserRepository : JpaRepository<RecruitmentUser, Long> {

    /**
     * 특정 사용자가 특정 모집 게시글에 지원한 내역 조회
     * - Post, SiteUser 데이터, JobSkills 정보를 즉시 로딩
     *
     * @param postId 모집 게시글 ID
     * @param userId 사용자 ID
     * @return 모집 지원 내역 (존재하지 않을 경우 빈 Optional 반환)
     */
    @Query("""
           SELECT DISTINCT ru FROM RecruitmentUser ru
           JOIN FETCH ru.post p
           JOIN FETCH ru.siteUser su
           JOIN FETCH su._jobSkillList
           WHERE p.postId = :postId AND su.id = :userId
       """)
    fun findByPostAndUser(
        @Param("postId") postId: Long,
        @Param("userId") userId: Long
    ): RecruitmentUser?

    /**
     * 특정 모집 게시글에 대해 지정된 상태의 모집 지원자 목록 조회
     * - Post 및 SiteUser, JobSkills 정보 즉시 로딩
     *
     * @param postId 모집 게시글 ID
     * @param status 조회할 모집 지원 상태 (예: APPLIED, ACCEPTED, REJECTED 등)
     * @param pageable 페이지네이션 정보
     * @return 해당 상태의 모집 지원자 목록 (페이징 결과)
     */
    @Query("""
           SELECT ru FROM RecruitmentUser ru
           JOIN FETCH ru.post p
           JOIN FETCH ru.siteUser su
           JOIN FETCH su._jobSkillList
           WHERE p.postId = :postId AND ru.status = :status
       """)
    fun findAllByPostAndStatus(
        @Param("postId") postId: Long,
        @Param("status") status: RecruitmentUserStatus,
        pageable: Pageable
    ): Page<RecruitmentUser>

    /**
     * 특정 사용자가 지정된 상태로 모집된 게시글 목록을 페이지네이션하여 조회
     * - Post 및 SiteUser, JobSkills 정보 즉시 로딩
     *
     * @param userId   사용자 ID
     * @param status   모집 지원 상태 (예: APPLIED, ACCEPTED, REJECTED 등)
     * @param pageable 페이지네이션 정보
     * @return 모집된 게시글 목록 (페이징 결과)
     */
    @Query("""
           SELECT ru FROM RecruitmentUser ru
           JOIN FETCH ru.post p
           JOIN FETCH ru.siteUser su
           JOIN FETCH su._jobSkillList
           WHERE su.id = :userId AND ru.status = :status
       """)
    fun findAllByUserAndStatus(
        @Param("userId") userId: Long,
        @Param("status") status: RecruitmentUserStatus,
        pageable: Pageable
    ): Page<RecruitmentUser>

    /**
     * 특정 사용자가 특정 모집 게시글에 승인된 상태로 존재하는지 검증
     *
     * @param postId 모집 게시글 ID
     * @param userId 사용자 ID
     * @return 승인 유저인지 검증
     */
    @Query("""
           SELECT CASE WHEN COUNT(ru) > 0 THEN TRUE ELSE FALSE END
           FROM RecruitmentUser ru
           WHERE ru.post.postId = :postId
             AND ru.siteUser.id = :userId
             AND ru.status = com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus.ACCEPTED
       """)
    fun existsAcceptedRecruitmentForPostAndUser(
        @Param(value = "postId") postId: Long,
        @Param(value = "userId") userId: Long
    ): Boolean

    /**
     * 특정 게시글에서 모집이 종료(CLOSED)된 상태에서 승인된(ACCEPTED) 모집 지원자 목록 조회
     *
     * @param postId 게시글 ID
     * @return 모집 종료(CLOSED)된 게시글에서 승인된 모집 지원자 목록
     */
    @Query("""
           SELECT ru FROM RecruitmentUser ru
           JOIN FETCH ru.post p
           WHERE p.postId = :postId
           AND p.recruitmentStatus = com.backend.domain.post.entity.RecruitmentStatus.CLOSED
           AND ru.status = com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus.ACCEPTED
           """)
    fun findAcceptedRecruitmentsForClosedPost(@Param("postId") postId: Long): List<RecruitmentUser>

    /**
     * 특정 게시글에 대해 승인된(ACCEPTED) 모집 지원자 수 조회
     *
     * @param postId 게시글 ID
     * @return 승인된 모집 지원자 수
     */
    @Query("""
           SELECT COALESCE(COUNT(ru), 0) FROM RecruitmentUser ru
           WHERE ru.post.postId = :postId 
           AND ru.status = com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus.ACCEPTED
           """)
    fun countAcceptedRecruitmentsByPost(@Param("postId") postId: Long): Int
}