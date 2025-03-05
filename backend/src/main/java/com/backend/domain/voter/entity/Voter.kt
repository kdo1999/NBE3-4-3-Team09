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
}
