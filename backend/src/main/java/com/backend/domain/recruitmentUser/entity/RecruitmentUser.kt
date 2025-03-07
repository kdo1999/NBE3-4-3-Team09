package com.backend.domain.recruitmentUser.entity

import com.backend.domain.post.entity.RecruitmentPost
import com.backend.domain.user.entity.SiteUser
import com.backend.global.baseentity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "recruitment_user")
class RecruitmentUser() : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_user_id")
    var id: Long? = null
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    lateinit var post: RecruitmentPost
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_user_id")
    lateinit var siteUser: SiteUser
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var status: RecruitmentUserStatus
        protected set

    constructor(post: RecruitmentPost, siteUser: SiteUser, status: RecruitmentUserStatus):this() {
        this.post = post
        this.siteUser = siteUser
        this.status = status
    }


    // 모집 게시판 작성자 모집 수락 메서드
    fun accept() {
        status = RecruitmentUserStatus.ACCEPTED // 모집 상태 완료로 변경
    }

    fun reject() {
        status = RecruitmentUserStatus.REJECTED
    }
}

