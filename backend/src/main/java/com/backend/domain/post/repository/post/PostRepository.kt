package com.backend.domain.post.repository.post

import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.post.dto.PostResponse
import com.backend.domain.post.entity.Post
import com.backend.domain.post.util.PostSearchCondition
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface PostRepository {

    // 게시글 단건 조회 메서드
    fun findById(postId: Long): Post?

    // 게시글 단건 조회 (fetch join 사용)
    fun findByIdFetch(postId: Long): Post?

    // 게시글 저장 메서드
    fun save(post: Post): Post

    // 게시글 삭제
    fun deleteById(postId: Long)

    // 게시글 전체 동적 조회 메서드
    fun findAll(postSearchCondition: PostSearchCondition, pageable: Pageable): Page<PostPageResponse>
    fun findRecruitmentAll(userId: Long, status: RecruitmentUserStatus, pageable: Pageable): Page<PostPageResponse>

    // 게시글 상세 조회 메서드
    fun findPostResponseById(postId: Long, siteUserId: Long): PostResponse?

}