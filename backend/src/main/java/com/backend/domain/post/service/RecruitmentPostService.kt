package com.backend.domain.post.service

import com.backend.domain.category.domain.CategoryName
import com.backend.domain.category.repository.CategoryRepository
import com.backend.domain.jobposting.repository.JobPostingRepository
import com.backend.domain.post.conveter.PostConverter
import com.backend.domain.post.dto.PostCreateResponse
import com.backend.domain.post.dto.RecruitmentPostRequest
import com.backend.domain.post.dto.RecruitmentPostResponse
import com.backend.domain.post.entity.RecruitmentPost
import com.backend.domain.post.repository.recruitment.RecruitmentPostRepository
import com.backend.domain.recruitmentUser.entity.RecruitmentUser
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import com.backend.domain.recruitmentUser.repository.RecruitmentUserRepository
import com.backend.domain.user.entity.SiteUser
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecruitmentPostService(
    private val categoryRepository: CategoryRepository,
    private val jobPostingRepository: JobPostingRepository,
    private val recruitmentUserRepository: RecruitmentUserRepository,
    private val recruitmentPostRepository: RecruitmentPostRepository
) {

    // ==============================
    //  1. 비즈니스 로직
    // ==============================

    /**
     * 모집 게시글을 조회합니다.
     *
     * @param postId   조회할 게시글 아이디
     * @param siteUser 로그인한 사용자
     * @return {@link RecruitmentPostResponse}
     * @throws GlobalException 게시글이 존재하지 않을 때 예외 발생
     */
    @Transactional(readOnly = true)
    fun findById(postId: Long, siteUser: SiteUser): RecruitmentPostResponse {
        return recruitmentPostRepository.findPostResponseById(postId, siteUser.id!!)
            ?: throw GlobalException(GlobalErrorCode.POST_NOT_FOUND)
    }

    /**
     * 모집 게시글을 생성합니다.
     *
     * @param recruitmentPostRequest 모집 게시글 관련 정보가 담긴 DTO
     * @param siteUser               현재 게시글을 작성하는 사용자
     * @return 생성된 게시글의 ID와 카테고리 ID를 포함한 응답 DTO
     * @throws GlobalException 카테고리 또는 채용 공고가 존재하지 않을 경우 예외 발생
     */
    @Transactional
    fun save(recruitmentPostRequest: RecruitmentPostRequest, siteUser: SiteUser): PostCreateResponse {
        val category = categoryRepository.findByName(CategoryName.RECRUITMENT.value)
            .orElseThrow{GlobalException(GlobalErrorCode.CATEGORY_NOT_FOUND)}

        val jobPosting = jobPostingRepository.findById(recruitmentPostRequest.jobPostingId)
            ?: throw GlobalException(GlobalErrorCode.JOB_POSTING_NOT_FOUND)

        val post = PostConverter.createPost(recruitmentPostRequest, category, siteUser, jobPosting)

        val savedPost = recruitmentPostRepository.save(post)

        // TODO: 추후 연관관계 매핑 후 수정할 것
        val recruitmentUser = RecruitmentUser(
            post = post,
            siteUser = siteUser,
            status = RecruitmentUserStatus.ACCEPTED
        )
        recruitmentUserRepository.save(recruitmentUser)

        return PostConverter.toPostCreateResponse(savedPost.postId!!, savedPost.category.id!!)
    }

    /**
     * 모집 게시글을 수정합니다.
     *
     * @param postId                 수정할 게시글의 ID
     * @param recruitmentPostRequest 수정할 정보가 담긴 DTO
     * @param siteUser               현재 게시글을 수정하는 사용자
     * @return 수정된 게시글 정보를 담은 DTO
     * @throws GlobalException 게시글이 존재하지 않거나, 작성자가 아닐 경우 예외 발생
     */
    @Transactional
    fun update(postId: Long, recruitmentPostRequest: RecruitmentPostRequest, siteUser: SiteUser): RecruitmentPostResponse {
        val findPost = getPost(postId)

        if (findPost.author.id != siteUser.id) {
            throw GlobalException(GlobalErrorCode.POST_NOT_AUTHOR)
        }

        validateAuthor(siteUser, findPost)

        findPost.updatePost(
            recruitmentPostRequest.subject,
            recruitmentPostRequest.content,
            recruitmentPostRequest.numOfApplicants
        )

        return PostConverter.toPostResponse(findPost, true, getCurrentAcceptedCount(postId), siteUser)
    }

    /**
     * 모집 게시글을 삭제합니다.
     *
     * @param postId   삭제할 게시글의 ID
     * @param siteUser 현재 게시글을 삭제하는 사용자
     * @throws GlobalException 게시글이 존재하지 않거나, 작성자가 아닐 경우 예외 발생
     */
    @Transactional
    fun delete(postId: Long, siteUser: SiteUser) {
        val findPost = getPost(postId)
        validateAuthor(siteUser, findPost)

        recruitmentPostRepository.deleteById(postId)
    }

    // ==============================
    //  2. 검증 메서드
    // ==============================

    /**
     * 게시글의 작성자를 검증합니다.
     *
     * @param siteUser 현재 사용자
     * @param findPost 조회된 게시글
     * @throws GlobalException 사용자가 게시글의 작성자가 아닐 경우 예외 발생
     */
    private fun validateAuthor(siteUser: SiteUser, findPost: RecruitmentPost) {
        if (findPost.author != siteUser) {
            throw GlobalException(GlobalErrorCode.POST_NOT_AUTHOR) // 작성자가 아닐 경우 예외 발생
        }
    }

    // ==============================
    //  3. DB 조회 메서드
    // ==============================

    /**
     * 게시글을 조회합니다.
     *
     * @param postId 모집 게시글 ID
     * @return 조회된 게시글 엔티티
     * @throws GlobalException 게시글이 존재하지 않을 경우 예외 발생
     */
    private fun getPost(postId: Long): RecruitmentPost {
        return recruitmentPostRepository.findByIdFetch(postId)
            ?: throw GlobalException(GlobalErrorCode.POST_NOT_FOUND)
    }

    /**
     * 게시글을 조회합니다.
     *
     * @param postId 모집 게시글 ID
     * @return 조회된 게시글에서 승인된 회원수 호출
     */
    private fun getCurrentAcceptedCount(postId: Long): Int {
        return recruitmentUserRepository.countAcceptedRecruitmentsByPost(postId)
    }
}