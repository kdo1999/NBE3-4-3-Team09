package com.backend.domain.user.dto.request

import com.backend.domain.user.entity.SiteUser

data class UserModifyProfileRequest(
    var introduction: String?,
    var job: String?,
    var jobSkills: List<JobSkillRequest>? = null
) {
    constructor(siteUser: SiteUser) : this(
        introduction = siteUser.introduction,
        job = siteUser.job,
        jobSkills = null
    )
}
