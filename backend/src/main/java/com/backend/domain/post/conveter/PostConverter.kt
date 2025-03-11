package com.backend.domain.post.conveter

import com.backend.domain.category.entity.Category
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.jobposting.entity.QJobPosting.jobPosting
import com.backend.domain.jobposting.repository.JobPostingRepository
import com.backend.domain.post.dto.*
import com.backend.domain.post.entity.Post
import com.backend.domain.post.entity.RecruitmentPost
import com.backend.domain.user.entity.SiteUser

object PostConverter {

    // 자유 게시글 저장할 떄
    fun createPost(
        freePostRequest: FreePostRequest, siteUser: SiteUser,
        category: Category
    ): Post {
        return Post(
            subject = freePostRequest.subject,
            content = freePostRequest.content,
            category = category,
            author = siteUser
        )
    }

    // 모집 게시글 저장할 때
    fun createPost(
        recruitmentPostRequest: RecruitmentPostRequest, category: Category,
        author: SiteUser, jobPosting: JobPosting): RecruitmentPost {
        return RecruitmentPost(
            subject = recruitmentPostRequest.subject,
            content = recruitmentPostRequest.content,
            category = category,
            author = author,
            jobPosting = jobPosting
        )
    }

    // 자유 게시글 응답 변환
    fun toPostResponse(post: Post, isAuthor: Boolean, currentUserId: Long) : PostResponse{
        return PostResponse(
            id = post.postId!!,
            subject = post.subject,
            content = post.content,
            categoryId = post.category.id!!,
            isAuthor = isAuthor,
            authorName = post.author.name,
            authorImg = post.author.profileImg?:"",
            voterCount = post.postVoterList.size.toLong(),
            isVoter = post.postVoterList.any { it.siteUser.id == currentUserId },
            createdAt = post.createdAt        )
    }
    // 모집 게시글 응답 변환
    fun toPostResponse(post: RecruitmentPost, isAuthor: Boolean, currentAcceptedCount: Int,
                       currentUser: SiteUser) : RecruitmentPostResponse{
        return RecruitmentPostResponse(
            id = post.postId!!,
            subject = post.subject,
            content = post.content,
            categoryId = post.category.id!!,
            isAuthor = isAuthor,
            authorName = post.author.name,
            authorImg = post.author.profileImg?:"",
            voterCount = post.postVoterList.size.toLong(),
            isVoter = post.postVoterList.any { it.siteUser == currentUser },
            createdAt = post.createdAt,
            jobPostingId = post.jobPosting.id!!,
            numOfApplicants = post.numOfApplicants ?: 0,
            recruitmentStatus = post.recruitmentStatus,
            currentAcceptedCount = currentAcceptedCount
        )
    }

    // 자유 게시글 생성
    fun toPostCreateResponse(postId: Long, categoryId: Long) : PostCreateResponse{
        return PostCreateResponse(
            postId = postId,
            categoryId = categoryId
        )
    }

}