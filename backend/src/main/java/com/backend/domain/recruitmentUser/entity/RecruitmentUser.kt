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
    private lateinit var _post: RecruitmentPost
    val post: RecruitmentPost
        get() = _post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_user_id")
    private lateinit var _siteUser: SiteUser
    val siteUser: SiteUser
        get() = _siteUser

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private lateinit var _status: RecruitmentUserStatus
    val status: RecruitmentUserStatus
        get() = _status

    constructor(post: RecruitmentPost, siteUser: SiteUser, status: RecruitmentUserStatus): this() {
        this._post = post
        this._siteUser = siteUser
        this._status = status
    }

    // 모집 게시판 작성자 모집 수락 메서드
    fun accept() {
        _status = RecruitmentUserStatus.ACCEPTED // 모집 상태 완료로 변경
    }

    fun reject() {
        _status = RecruitmentUserStatus.REJECTED
    }
}

