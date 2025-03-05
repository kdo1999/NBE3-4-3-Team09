package com.backend.domain.voter.entity

import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.post.entity.Post
import com.backend.domain.user.entity.SiteUser
import com.backend.domain.voter.domain.VoterType
import jakarta.persistence.*

@Entity
@Table(name = "voter")
class Voter {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voter_id")
	var id: Long? = null
	protected set

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
	var jobPosting: JobPosting? = null
	protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
	protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_user_id")
    lateinit var siteUser: SiteUser
	protected set

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    lateinit var voterType: VoterType
	protected set


	constructor(jobPosting: JobPosting, siteUser: SiteUser, voterType: VoterType) {
		this.jobPosting = jobPosting
		this.siteUser = siteUser
		this.voterType = voterType
	}

	constructor(post: Post, siteUser: SiteUser, voterType: VoterType) {
		this.post = post
		this.siteUser = siteUser
		this.voterType = voterType
	}

	companion object {
        @JvmStatic
        fun builder() = VoterBuilder()
    }

    class VoterBuilder {
        private var id: Long? = null
        private var jobPosting: JobPosting? = null
        private var post: Post? = null
        private var siteUser: SiteUser? = null
        private var voterType: VoterType? = null

        fun id(id: Long?) = apply { this.id = id }
        fun jobPosting(jobPosting: JobPosting?) = apply { this.jobPosting = jobPosting }
        fun post(post: Post?) = apply { this.post = post }
        fun siteUser(siteUser: SiteUser?) = apply { this.siteUser = siteUser }
        fun voterType(voterType: VoterType?) = apply { this.voterType = voterType }

        fun build(): Voter {
            requireNotNull(siteUser) { "siteUser must not be null" }
            requireNotNull(voterType) { "voterType must not be null" }
            
            return when {
                jobPosting != null -> Voter(jobPosting!!, siteUser!!, voterType!!)
                post != null -> Voter(post!!, siteUser!!, voterType!!)
                else -> throw IllegalStateException("Either jobPosting or post must be set")
            }
        }
    }
}
