package com.backend.domain.post.entity

import com.backend.domain.category.entity.Category
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.user.entity.SiteUser
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@DiscriminatorValue("recruitment")
class RecruitmentPost: Post {
    @Column(nullable = false)
    lateinit var recruitmentClosingDate: ZonedDateTime
        protected set

    @Column(nullable = false)
    var numOfApplicants: Int? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var recruitmentStatus: RecruitmentStatus
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    lateinit var jobPosting: JobPosting
        protected set

    constructor(
        subject: String,
        content: String,
        category: Category,
        author: SiteUser,
        jobPosting: JobPosting,
        recruitmentClosingDate: ZonedDateTime,
        ): super(subject, content, category, author) {
        this.jobPosting = jobPosting
        this.recruitmentClosingDate = recruitmentClosingDate
    }

    fun updatePost(
        subject: String,
        content: String,
        numOfApplicants: Int,
        recruitmentClosingDate: ZonedDateTime) {
        super.updatePost(subject, content)
        this.numOfApplicants = numOfApplicants
        this.recruitmentClosingDate = recruitmentClosingDate
    }

    fun updateRecruitmentStatus(recruitmentStatus: RecruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus
    }


}