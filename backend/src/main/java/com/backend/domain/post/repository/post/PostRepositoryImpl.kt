package com.backend.domain.post.repository.post

import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.post.dto.PostResponse
import com.backend.domain.post.entity.Post
import com.backend.domain.post.util.PostSearchCondition
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
    private val postQueryRepository: PostQueryRepository
) : PostRepository {

    override fun findById(postId: Long): Post? {
        return postJpaRepository.findById(postId).orElse(null)
    }

    override fun findByIdFetch(postId: Long): Post? {
        return postJpaRepository.findByIdFetch(postId)
    }

    override fun save(post: Post): Post {
        return postJpaRepository.save(post)
    }

    override fun deleteById(postId: Long) {
        postJpaRepository.deleteById(postId)
    }

    override fun findAll(postSearchCondition: PostSearchCondition, pageable: Pageable): Page<PostPageResponse> {
        return postQueryRepository.findAll(postSearchCondition, pageable)
    }

    override fun findRecruitmentAll(userId: Long, status: RecruitmentUserStatus, pageable: Pageable): Page<PostPageResponse> {
        return postQueryRepository.findRecruitmentAll(pageable, userId, status)
    }

    override fun findPostResponseById(postId: Long, siteUserId: Long): PostResponse? {
        return postQueryRepository.findPostResponseById(postId, siteUserId)
    }
}
