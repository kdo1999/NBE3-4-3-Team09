package com.backend.domain.post.service

import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.post.repository.post.PostRepository
import com.backend.domain.post.util.PostSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {

    /**
     * 게시글 전체 동적 조회 메서드 입니다.
     *
     * @param postSearchCondition 검색 조건
     * @return {@link Page<PostPageResponse>}
     */
    @Transactional(readOnly = true)
    fun findAll(postSearchCondition: PostSearchCondition): Page<PostPageResponse> {
        val pageNum = postSearchCondition.pageNum ?: 0
        val pageSize = postSearchCondition.pageSize ?: 10
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)

        return postRepository.findAll(postSearchCondition, pageable)
    }
}