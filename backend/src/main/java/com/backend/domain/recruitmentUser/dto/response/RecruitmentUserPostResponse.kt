package com.backend.domain.recruitmentUser.dto.response

import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import org.springframework.data.domain.Page

data class RecruitmentUserPostResponse(
	val status: RecruitmentUserStatus,
	val postPageResponses: Page<PostPageResponse>
) {

}