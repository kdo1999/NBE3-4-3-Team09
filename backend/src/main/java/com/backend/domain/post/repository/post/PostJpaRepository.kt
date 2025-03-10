package com.backend.domain.post.repository.post

import com.backend.domain.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PostJpaRepository : JpaRepository<Post, Long> {

    @Query("select p from Post p left join fetch p.author where p.postId = :postId")
    fun findByIdFetch(@Param("postId") postId: Long): Post?
}