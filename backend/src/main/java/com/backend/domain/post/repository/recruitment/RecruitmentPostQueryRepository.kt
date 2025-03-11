package com.backend.domain.post.repository.recruitment

import com.backend.domain.post.dto.QRecruitmentPostResponse
import com.backend.domain.post.dto.RecruitmentPostResponse
import com.backend.domain.post.entity.QRecruitmentPost.recruitmentPost
import com.backend.domain.recruitmentUser.entity.QRecruitmentUser.recruitmentUser
import com.backend.domain.voter.entity.QVoter.voter
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class RecruitmentPostQueryRepository( private val queryFactory: JPAQueryFactory
) {

    fun findPostResponseById(postId: Long, siteUserId: Long): RecruitmentPostResponse? {
        val postResponse = queryFactory.selectDistinct(
            QRecruitmentPostResponse(
                recruitmentPost.postId, recruitmentPost.subject, recruitmentPost.content, recruitmentPost.category.id,
                recruitmentPost.author.id.eq(siteUserId), recruitmentPost.author.name,
                recruitmentPost.author.profileImg, voter.countDistinct(),
                voter.siteUser.id.eq(siteUserId), recruitmentPost.createdAt,
                recruitmentPost.recruitmentClosingDate, recruitmentPost.jobPosting.id,
                recruitmentPost.numOfApplicants, recruitmentPost.recruitmentStatus,
                recruitmentUser.countDistinct().intValue()
            )
        )
            .from(recruitmentPost)
            .leftJoin(recruitmentPost.category)
            .leftJoin(recruitmentPost.author)
            .leftJoin(recruitmentPost._postVoterList, voter)
            .leftJoin(recruitmentUser)
            .on(recruitmentUser.post.postId.eq(postId))
            .groupBy(
                recruitmentPost.postId, recruitmentPost.subject, recruitmentPost.content, recruitmentPost.category.id,
                recruitmentPost.author.id, recruitmentPost.author.name, recruitmentPost.author.profileImg,
                voter.siteUser.id, recruitmentPost.createdAt, recruitmentPost.numOfApplicants,
                recruitmentPost.recruitmentStatus, recruitmentPost.recruitmentClosingDate
            )
            .where(recruitmentPost.postId.eq(postId))
            .fetchOne()

        return postResponse
    }
}