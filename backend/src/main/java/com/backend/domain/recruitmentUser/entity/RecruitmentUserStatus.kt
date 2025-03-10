package com.backend.domain.recruitmentUser.entity

import com.fasterxml.jackson.annotation.JsonCreator

enum class RecruitmentUserStatus {
    APPLIED,   // 지원 완료
    ACCEPTED,  // 수락됨
    REJECTED,  // 거절됨
    NOT_APPLIED; // 지원하지 않음

    companion object{
		@JsonCreator
		@JvmStatic
		fun from(param: String): RecruitmentUserStatus? =
			entries.firstOrNull { it.name.equals(param, ignoreCase = true) }
	}
}
