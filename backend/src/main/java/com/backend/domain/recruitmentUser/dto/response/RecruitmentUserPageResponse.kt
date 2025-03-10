package com.backend.domain.recruitmentUser.dto.response

import com.backend.domain.recruitmentUser.entity.RecruitmentUser
import org.springframework.data.domain.Page

/**
 * 모집 지원자 목록을 포함하는 DTO (페이징 적용)
 *
 * @param postId              모집 게시글 ID
 * @param recruitmentUserList 모집 지원자 목록 (페이징 지원)
 */
data class RecruitmentUserPageResponse(
        val postId: Long,
        val recruitmentUserList: Page<RecruitmentUserDetail>
) {
    companion object {

        /**
         * 모집 지원자 목록을 Page<RecruitmentUserDetail> 형태로 변환
         */
        @JvmStatic
        fun from(
            postId: Long,
            recruitmentUsers: Page<RecruitmentUser>): RecruitmentUserPageResponse {
            return RecruitmentUserPageResponse(
                postId,
                recruitmentUsers.map(RecruitmentUserDetail::from)
            )
        }
    }
}