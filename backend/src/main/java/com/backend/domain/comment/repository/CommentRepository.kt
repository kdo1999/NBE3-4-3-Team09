//package com.backend.domain.comment.repository
//
//import com.backend.domain.comment.dto.response.CommentResponseDto
//import com.backend.domain.comment.entity.Comment
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.Pageable
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import java.util.Optional
//
//interface CommentRepository : JpaRepository<Comment, Long> {
//
////    @Query("SELECT c FROM Comment c JOIN FETCH c.siteUser WHERE c.id = :commentId")
////    fun findById(@Param("commentId") commentId: Long): Optional<Comment>
//
//    fun findByPost_PostId(@Param("postId") postId: Long, pageable: Pageable): Page<Comment>
//
//    @Query("""
//        SELECT com.backend.domain.comment.dto.response.CommentResponseDto (
//            c.id, c.content, c.createdAt, c.modifiedAt, c.siteUser.profileImg, c.siteUser.name
//        )
//        FROM Comment c
//        LEFT JOIN c.siteUser
//        LEFT JOIN c.post
//        WHERE c.post.postId = :postId
//    """)
//    fun findAllByPostId(@Param("postId") postId: Long, pageable: Pageable): Page<CommentResponseDto>
//
//    @Query("""
//        SELECT c
//        FROM Comment c
//        LEFT JOIN FETCH c.siteUser
//        LEFT JOIN FETCH c.post
//        WHERE c.post.postId = :postId
//    """)
//    fun findAll(@Param("postId") postId: Long, pageable: Pageable): Page<Comment>
//}
