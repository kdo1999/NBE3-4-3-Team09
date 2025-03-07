package com.backend.domain.recruitmentUser.dto.response

import com.backend.domain.recruitmentUser.entity.RecruitmentUser
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import com.backend.domain.user.dto.response.UserGetProfileResponse

/**
 * 모집 지원자의 세부 정보를 포함하는 DTO
 *
 * @param userId      지원자 ID
 * @param status      모집 지원 상태
 * @param userProfile 지원자 프로필 정보
 */
data class RecruitmentUserDetail(
    val userId: Long,
    val status: RecruitmentUserStatus,
    val userProfile: UserGetProfileResponse
) {
    companion object {
        /**
         * 모집 지원 정보를 `RecruitmentUserDetail`로 변환하는 팩토리 메서드
         *
         * @param recruitmentUser 모집 지원 엔티티
         * @return 변환된 `RecruitmentUserDetail`
         */
        fun from(recruitmentUser: RecruitmentUser): RecruitmentUserDetail {
            return RecruitmentUserDetail(
                recruitmentUser.siteUser.id!!,
                recruitmentUser.status,
                UserGetProfileResponse(recruitmentUser.siteUser)
            )
        }
    }
}
