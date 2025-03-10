package com.backend.domain.recruitmentUser.service

import com.backend.domain.post.entity.RecruitmentPost
import com.backend.domain.post.entity.RecruitmentStatus
import com.backend.domain.post.repository.recruitment.RecruitmentPostRepository
import com.backend.domain.recruitmentUser.dto.response.RecruitmentUserPageResponse
import com.backend.domain.recruitmentUser.entity.RecruitmentUser
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import com.backend.domain.recruitmentUser.repository.RecruitmentUserRepository
import com.backend.domain.user.entity.SiteUser
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import com.backend.global.mail.service.MailService
import com.backend.global.mail.util.TemplateName
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecruitmentAuthorService(
    private val mailService: MailService,
    private val recruitmentUserRepository: RecruitmentUserRepository,
    private val recruitmentPostRepository: RecruitmentPostRepository
) {

    // ==============================
    //  1. 모집 지원 처리 (승인 / 거절)
    // ==============================

    /**
     * 모집 지원 승인: 작성자가 특정 지원자의 모집 신청을 승인합니다.
     */
    @Transactional
    fun recruitmentAccept(author: SiteUser, postId: Long, userId: Long) {
        val post = validateAuthorAndGetPost(author, postId)
        val recruitmentUser = getRecruitmentUser(userId, postId)

        validateRecruitmentNotClosed(post)
        validateRecruitmentUserStatus(recruitmentUser)

        // 모집 승인 처리
        recruitmentUser.accept()

        // 모집 완료 여부 확인 후 상태 변경
        updateRecruitmentStatus(post)
    }

    /**
     * 모집 지원 거절: 작성자가 특정 지원자의 모집 신청을 거절합니다.
     */
    @Transactional
    fun recruitmentReject(author: SiteUser, postId: Long, userId: Long) {
        val post = validateAuthorAndGetPost(author, postId)
        val recruitmentUser = getRecruitmentUser(userId, postId)

        validateRecruitmentNotClosed(post)
        validateRecruitmentUserStatus(recruitmentUser)

        recruitmentUser.reject()
    }

    // ==============================
    //  2. 모집 지원자 조회
    // ==============================

    /**
     * 모집 지원자 목록 조회
     */
    @Transactional(readOnly = true)
    fun getAppliedUserList(author: SiteUser, postId: Long, pageable: Pageable): RecruitmentUserPageResponse {
        val post = validateAuthorAndGetPost(author, postId)
        val appliedUsers: Page<RecruitmentUser> =
            recruitmentUserRepository.findAllByPostAndStatus(post.postId!!, RecruitmentUserStatus.APPLIED, pageable)

        return RecruitmentUserPageResponse.from(postId, appliedUsers)
    }

    /**
     * 모집 승인된 참여자 목록 조회
     */
    @Transactional(readOnly = true)
    fun getAcceptedUserList(author: SiteUser, postId: Long, pageable: Pageable): RecruitmentUserPageResponse {
        val post = validateAuthorAndGetPost(author, postId)
        val acceptedUsers: Page<RecruitmentUser> =
            recruitmentUserRepository.findAllByPostAndStatus(post.postId!!, RecruitmentUserStatus.ACCEPTED, pageable)

        return RecruitmentUserPageResponse.from(postId, acceptedUsers)
    }

    // ==============================
    //  3. 검증 메서드
    // ==============================

    /**
     * 모집 신청 내역 조회
     */
    private fun getRecruitmentUser(userId: Long, postId: Long): RecruitmentUser {
        return recruitmentUserRepository.findByPostAndUser(postId, userId)
            ?: throw GlobalException(GlobalErrorCode.RECRUITMENT_NOT_FOUND)
    }


    /**
     * 모집이 종료된 경우 예외 발생
     */
    private fun validateRecruitmentNotClosed(post: RecruitmentPost) {
        if (post.recruitmentStatus == RecruitmentStatus.CLOSED) {
            throw GlobalException(GlobalErrorCode.RECRUITMENT_CLOSED)
        }
    }

    /**
     * 모집 신청자의 상태 검증
     */
    private fun validateRecruitmentUserStatus(recruitmentUser: RecruitmentUser) {
        if (recruitmentUser.status != RecruitmentUserStatus.APPLIED) {
            throw GlobalException(GlobalErrorCode.INVALID_RECRUITMENT_STATUS)
        }
    }

    /**
     * 모집 게시글 검증 및 조회
     */
    private fun validateAuthorAndGetPost(author: SiteUser, postId: Long): RecruitmentPost {
        val post = recruitmentPostRepository.findByIdFetch(postId)
            .orElseThrow { GlobalException(GlobalErrorCode.POST_NOT_FOUND) }

        if (post.author.id != author.id) {
            throw GlobalException(GlobalErrorCode.POST_NOT_AUTHOR)
        }

        return post
    }

    /**
     * 모집 상태 업데이트
     */
    @Transactional
    fun updateRecruitmentStatus(post: RecruitmentPost) {
        if ((post.numOfApplicants ?: 0) <= (recruitmentUserRepository.countAcceptedRecruitmentsByPost(post.postId!!))) {
            post.updateRecruitmentStatus(RecruitmentStatus.CLOSED)
        }

        recruitmentPostRepository.save(post)

        // 모집 상태가 CLOSED인 게시글에서 ACCEPTED 상태의 유저 이메일 리스트 반환
        val emailList = recruitmentUserRepository.findAcceptedRecruitmentsForClosedPost(post.postId!!)
            .map { it.siteUser.email }

        mailService.sendDeliveryStartEmailAsync(emailList, TemplateName.RECRUITMENT_CHAT, post.postId!!)
    }
}
