package com.backend.domain.comment.entity

import com.backend.domain.post.entity.Post
import com.backend.domain.user.entity.SiteUser
import com.backend.global.baseentity.BaseEntity
import jakarta.persistence.*

@Entity
class Comment : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null
        protected set

    @Column(nullable = false, length = 500)
    lateinit var content: String
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    lateinit var post: Post
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var siteUser: SiteUser
        protected set

    constructor(content: String, post: Post, siteUser: SiteUser) : super() {
        this.content = content
        this.post = post
        this.siteUser = siteUser
    }


    fun changeContent(newContent: String) {
        this.content = newContent
    }
}