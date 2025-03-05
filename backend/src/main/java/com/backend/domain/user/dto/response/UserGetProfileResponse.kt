package com.backend.domain.user.dto.response

import com.backend.domain.comment.entity.Comment
import com.backend.domain.jobskill.dto.JobSkillResponse
import com.backend.domain.jobskill.entity.JobSkill
import com.backend.domain.post.entity.Post
import com.backend.domain.user.entity.SiteUser
import java.time.ZonedDateTime

data class UserGetProfileResponse(
    val name: String?,
    val email: String?,
    val introduction: String?,
    val job: String?,
//    val jobSkills: List<JobSkillResponse>?,
    val profileImg: String?,
//    val posts: List<UserPostResponse>,
//    val comments: List<UserCommentResponse>
) {
    constructor(siteUser: SiteUser) : this(
        name = siteUser.name,
        email = siteUser.email,
        introduction = siteUser.introduction,
        job = siteUser.job,
//        jobSkills = siteUser.jobSkillList?.map { jobSkill ->
//            JobSkillResponse.builder()
//                .code(jobSkill.code)
//                .name(jobSkill.name)
//                .build()
//        },
        profileImg = siteUser.profileImg,
//        posts = siteUser.postLIst.map { UserPostResponse(it) },
//        comments = siteUser.commentLIst.map { UserCommentResponse(it) }
    )
}

//data class UserPostResponse(
//    val postId: Long,
//    val subject: String,
//    val createdAt: ZonedDateTime
//) {
//    constructor(post: Post) : this(
//        postId = post.postId,
//        subject = post.subject,
//        createdAt = post.createdAt
//    )
//}
//
//data class UserCommentResponse(
//    val commentId: Long,
//    val content: String,
//    val postId: Long,
//    val createdAt: ZonedDateTime
//) {
//    constructor(comment: Comment) : this(
//        commentId = comment.id,
//        content = comment.content,
//        postId = comment.post.postId,
//        createdAt = comment.createdAt
//    )
//}
