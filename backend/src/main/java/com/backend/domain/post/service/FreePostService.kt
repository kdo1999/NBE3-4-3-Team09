package com.backend.domain.post.service

import com.backend.domain.category.domain.CategoryName
import com.backend.domain.category.repository.CategoryRepository
import com.backend.domain.post.conveter.PostConverter
import com.backend.domain.post.dto.FreePostRequest
import com.backend.domain.post.dto.PostCreateResponse
import com.backend.domain.post.dto.PostResponse
import com.backend.domain.post.repository.post.PostRepository
import com.backend.domain.user.entity.SiteUser
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FreePostService(
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository
) {

    /**
     * 게시글 조회하는 메서드 입니다.
     *
     * @param postId   조회할 게시글 아이디
     * @param siteUser 로그인한 사용자
     * @return {@link PostResponse}
     * @throws GlobalException 게시글이 존재하지 않을 때 예외 발생
     */
    @Transactional(readOnly = true)
    fun findById(postId: Long, siteUser: SiteUser): PostResponse {
        return postRepository.findPostResponseById(postId, siteUser.id!!)
            ?: throw GlobalException(GlobalErrorCode.POST_NOT_FOUND)
    }

    /**
     * 게시글 저장 메서드 입니다.
     *
     * @param freePostRequest 자유 게시글 관련 정보가 담긴 DTO
     * @param siteUser        작성할 사용자
     * @return {@link PostCreateResponse}
     * @throws GlobalException 카테고리가 존재하지 않을 때 발생
     */
    @Transactional
    fun save(freePostRequest: FreePostRequest, siteUser: SiteUser): PostCreateResponse {
        log.info { "free=$freePostRequest" }

        val findCategory = categoryRepository.findByName(CategoryName.FREE.value).orElseThrow{
            throw GlobalException(GlobalErrorCode.CATEGORY_NOT_FOUND)
        }

        val savePost = PostConverter.createPost(freePostRequest, siteUser, findCategory)
        val savedPost = postRepository.save(savePost)

        return PostConverter.toPostCreateResponse(savedPost.postId!!, findCategory.id!!)
    }

    /**
     * 게시글 수정 메서드 입니다.
     *
     * @param postId          수정할 게시글 ID
     * @param freePostRequest 수정할 데이터를 담은 DTO
     * @param siteUser        로그인한 사용자
     * @return {@link PostResponse}
     * @throws GlobalException
     */
    @Transactional
    fun update(postId: Long, freePostRequest: FreePostRequest, siteUser: SiteUser): PostResponse {
        val target = postRepository.findByIdFetch(postId)
            ?: throw GlobalException(GlobalErrorCode.POST_NOT_FOUND)

        if (target.author.id != siteUser.id) {
            throw GlobalException(GlobalErrorCode.POST_NOT_AUTHOR)
        }

        target.updatePost(freePostRequest.subject, freePostRequest.content)
        val updatedPost = postRepository.save(target)

        return PostConverter.toPostResponse(updatedPost, true, siteUser.id!!)
    }

    /**
     * 게시글 삭제 메서드 입니다.
     *
     * @param postId   삭제할 게시글 ID
     * @param siteUser 로그인한 사용자
     */
    @Transactional
    fun delete(postId: Long, siteUser: SiteUser) {
        val findPost = postRepository.findByIdFetch(postId)
            ?: throw GlobalException(GlobalErrorCode.POST_NOT_FOUND)

        if (findPost.author.id != siteUser.id) {
            throw GlobalException(GlobalErrorCode.POST_NOT_AUTHOR)
        }

        postRepository.deleteById(findPost.postId!!)
    }
}