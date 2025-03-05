package com.backend.domain.comment.entity

import com.backend.domain.post.entity.Post
import com.backend.domain.user.entity.SiteUser
import com.backend.global.baseentity.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자
class KComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long? = null,

    @Column(nullable = false, length = 500)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val siteUser: SiteUser
) : BaseEntity() {

    fun changeContent(newContent: String) {
        this.content = newContent
    }
}