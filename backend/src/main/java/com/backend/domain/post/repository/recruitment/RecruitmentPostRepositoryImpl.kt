package com.backend.domain.post.repository.recruitment

import com.backend.domain.post.dto.RecruitmentPostResponse
import com.backend.domain.post.entity.RecruitmentPost
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class RecruitmentPostRepositoryImpl(
    private val recruitmentPostJpaRepository: RecruitmentPostJpaRepository,
    private val recruitmentPostQueryRepository: RecruitmentPostQueryRepository
) : RecruitmentPostRepository {

    override fun findById(id: Long): Optional<RecruitmentPost> {
        return recruitmentPostJpaRepository.findById(id)
    }

    override fun findByIdFetch(id: Long): Optional<RecruitmentPost> {
        return recruitmentPostJpaRepository.findByIdFetch(id)
    }

    override fun save(recruitmentPost: RecruitmentPost): RecruitmentPost {
        return recruitmentPostJpaRepository.save(recruitmentPost)
    }

    override fun deleteById(id: Long) {
        recruitmentPostJpaRepository.deleteById(id)
    }

    override fun findAll(): List<RecruitmentPost> {
        return recruitmentPostJpaRepository.findAll()
    }

    override fun findPostResponseById(postId: Long, siteUserId: Long): Optional<RecruitmentPostResponse> {
        return recruitmentPostQueryRepository.findPostResponseById(postId, siteUserId)
    }
}