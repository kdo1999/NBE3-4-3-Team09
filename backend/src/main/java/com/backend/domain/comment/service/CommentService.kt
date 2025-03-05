//package com.backend.domain.comment.service
//
//import com.backend.domain.comment.dto.request.CommentRequestDto
//import com.backend.domain.comment.dto.response.CommentCreateResponseDto
//import com.backend.domain.comment.dto.response.CommentModifyResponseDto
//import com.backend.domain.comment.dto.response.CommentResponseDto
//import com.backend.domain.comment.entity.Comment
//import com.backend.domain.comment.repository.CommentRepository
//import com.backend.domain.post.repository.post.PostRepository
//import com.backend.domain.user.entity.SiteUser
//import com.backend.domain.user.repository.UserRepository
//import com.backend.global.exception.GlobalErrorCode
//import com.backend.global.exception.GlobalException
//import com.backend.global.security.custom.CustomUserDetails
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Pageable
//import org.springframework.data.domain.Sort
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class CommentService(
//    private val commentRepository: CommentRepository,
//    private val postRepository: PostRepository,
//    private val userRepository: UserRepository
//) {
//
//    @Transactional
//    fun createComment(dto: CommentRequestDto, postId: Long, user: CustomUserDetails): CommentCreateResponseDto {
//        val findPost = postRepository.findById(postId).orElseThrow {
//            GlobalException(GlobalErrorCode.POST_NOT_FOUND)
//        }
//
//        val comment = Comment(
//            content = dto.content,
//            post = findPost,
//            siteUser = user.siteUser
//        )
//
//        val savedComment = commentRepository.save(comment)
//        return CommentCreateResponseDto.convertEntity(savedComment)
//    }
//
//    @Transactional
//    fun modifyComment(postId: Long, commentId: Long, dto: CommentRequestDto, user: CustomUserDetails): CommentModifyResponseDto {
//        postRepository.findById(postId).orElseThrow {
//            GlobalException(GlobalErrorCode.POST_NOT_FOUND)
//        }
//
//        val comment = commentRepository.findById(commentId).orElseThrow {
//            GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND)
//        }
//
//        if (user.siteUser.id != comment.siteUser.id) {
//            throw GlobalException(GlobalErrorCode.COMMENT_NOT_AUTHOR)
//        }
//
//        comment.changeContent(dto.content)
//        val modifiedComment = commentRepository.save(comment)
//        return CommentModifyResponseDto.convertEntity(modifiedComment, true)
//    }
//
//    @Transactional
//    fun deleteComment(commentId: Long, user: SiteUser) {
//        val findComment = commentRepository.findById(commentId).orElseThrow {
//            GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND)
//        }
//
//        if (findComment.siteUser.id != user.id) {
//            throw GlobalException(GlobalErrorCode.COMMENT_NOT_AUTHOR)
//        }
//
//        commentRepository.deleteById(commentId)
//    }
//
////    @Transactional(readOnly = true)
////    fun getComments(postId: Long, page: Int, size: Int, siteUser: SiteUser): Page<CommentResponseDto> {
////        val pageable: Pageable = PageRequest.of(page, size, Sort.by("createdAt").descending())
////        val allByPostId: Page<Comment> = commentRepository.findAll(postId, pageable)
////
////        return allByPostId.map { c ->
////            CommentResponseDto(
////                id = c.id,
////                authorName = c.siteUser.name,
////                profileImageUrl = c.siteUser.profileImg,
////                content = c.content,
////                createdAt = c.createdAt,
////                modifiedAt = c.modifiedAt,
////                isAuthor = c.siteUser.id == siteUser.id
////            )
////        }
////    }
//}
