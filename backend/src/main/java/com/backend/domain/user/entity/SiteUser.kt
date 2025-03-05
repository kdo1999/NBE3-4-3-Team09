package com.backend.domain.user.entity

import com.backend.domain.comment.entity.Comment
import com.backend.domain.jobskill.entity.JobSkill
import com.backend.domain.post.entity.Post
import com.backend.domain.voter.entity.Voter
import com.backend.global.baseentity.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import lombok.*

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
class SiteUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    var id: Long? = null, // 유저 고유 식별 id

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "")
    var email: String? = null, // 사용자 이메일

    @Column(name = "password", nullable = false)
    var password: String? = null, // 사용자 비밀번호

    @Column(name = "name", nullable = false)
    var name: String? = null, // 사용자 이름

    @Column(name = "introduction", nullable = true)
    var introduction: String? = null, // 사용자 자기소개

    @Column(name = "job", nullable = true)
    var job: String? = null, // 사용자 직무

    @Column(name = "user_role", nullable = false)
    var userRole: String? = null, // 사용자 권한

    var kakaoId: String? = null, // 카카오 고유 식별 id

    var profileImg: String? = null, // 카카오 프로필 이미지 URL

    @ManyToMany
    @JoinTable(
        name = "user_job_skill",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "job_skill_id")]
    )
    var jobSkills: MutableList<JobSkill> = mutableListOf(), // 사용자 직무 스킬

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL])
    var posts: MutableList<Post> = mutableListOf(), // 사용자가 작성한 게시글

    @OneToMany(mappedBy = "siteUser", cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf(), // 사용자가 작성한 댓글

    @OneToMany(mappedBy = "siteUser", cascade = [CascadeType.ALL])
    var voters: MutableList<Voter> = mutableListOf()
) : BaseEntity() {
    constructor(
        id: Long?,
        email: String?,
        userRole: String?
    ) : this() {
        this.id = id
        this.email = email
        this.userRole = userRole
    }

    constructor(
        email: String?,
        name: String?,
        password: String?,
        userRole: String?
    ) : this() {
        this.email = email
        this.name = name
        this.password = password
        this.userRole = userRole
    }

    constructor(
        id: Long?,
        name: String?,
        email: String?,
        kakaoId: String?,
        profileImg: String?,
        password: String?,
        userRole: String?
    ) : this() {
        this.id = id
        this.name = name
        this.email = email
        this.kakaoId = kakaoId
        this.profileImg = profileImg
        this.password = password
        this.userRole = userRole
    }

    constructor(
        name: String?,
        email: String?,
        kakaoId: String?,
        profileImg: String?,
        password: String?,
        userRole: String?
    ) : this() {
        this.name = name
        this.email = email
        this.kakaoId = kakaoId
        this.profileImg = profileImg
        this.password = password
        this.userRole = userRole
    }

    fun modifyProfile(introduction: String?, job: String?) {
        if (introduction != null) this.introduction = introduction
        if (job != null) this.job = job
    }

    fun update(name: String?, profileImg: String?): SiteUser {
        this.name = name
        this.profileImg = profileImg
        return this
    }

    // 자바의 builder 기능을 쓰기 위한 코드
//    companion object {
//        @JvmStatic
//        fun builder() = SiteUserBuilder()
//    }
//
//    class SiteUserBuilder {
//        private var id: Long? = null
//        private var email: String? = null
//        private var password: String? = null
//        private var name: String? = null
//        private var introduction: String? = null
//        private var job: String? = null
//        private var userRole: String? = null
//        private var kakaoId: String? = null
//        private var profileImg: String? = null
//        private var jobSkills: MutableList<JobSkill> = mutableListOf()
//        private var posts: MutableList<Post> = mutableListOf()
//        private var comments: MutableList<Comment> = mutableListOf()
//        private var voters: MutableList<Voter> = mutableListOf()
//
//        fun id(id: Long?) = apply { this.id = id }
//        fun email(email: String?) = apply { this.email = email }
//        fun password(password: String?) = apply { this.password = password }
//        fun name(name: String?) = apply { this.name = name }
//        fun introduction(introduction: String?) = apply { this.introduction = introduction }
//        fun job(job: String?) = apply { this.job = job }
//        fun userRole(userRole: String?) = apply { this.userRole = userRole }
//        fun kakaoId(kakaoId: String?) = apply { this.kakaoId = kakaoId }
//        fun profileImg(profileImg: String?) = apply { this.profileImg = profileImg }
//        fun jobSkills(jobSkills: MutableList<JobSkill>) = apply { this.jobSkills = jobSkills }
//        fun posts(posts: MutableList<Post>) = apply { this.posts = posts }
//        fun comments(comments: MutableList<Comment>) = apply { this.comments = comments }
//        fun voters(voters: MutableList<Voter>) = apply { this.voters = voters }
//
//        fun build() = SiteUser(
//            id = id,
//            email = email,
//            password = password,
//            name = name,
//            introduction = introduction,
//            job = job,
//            userRole = userRole,
//            kakaoId = kakaoId,
//            profileImg = profileImg,
//            jobSkills = jobSkills,
//            posts = posts,
//            comments = comments,
//            voters = voters
//        )
//    }
}
