package com.backend.domain.post.entity

import com.backend.domain.category.entity.Category
import com.backend.domain.comment.entity.Comment
import com.backend.domain.user.entity.SiteUser
import com.backend.domain.voter.entity.Voter
import com.backend.global.baseentity.BaseEntity
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "post_type")
@Table(name = "post")
open class Post( // 상속하려면 open class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val postId: Long?= null,

    @Column(nullable = false)
    var subject: String,

    @Column(nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val author: SiteUser
) : BaseEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = [CascadeType.REMOVE])
    private var _postCommentList: MutableList<Comment> = mutableListOf()

    val postCommentList: List<Comment>
        get() = _postCommentList.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = [CascadeType.REMOVE])
    private var _postVoterList: MutableList<Voter> = mutableListOf()

    val postVoterList: List<Voter>
        get() = _postVoterList.toList()


    // 게시글 수정 메서드
    fun updatePost(subject: String, content: String){
        this.subject = subject
        this.content = content
    }
}
