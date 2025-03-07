package com.backend.domain.post.entity

import com.backend.domain.category.entity.Category
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.jobposting.repository.JobPostingRepository
import com.backend.domain.post.dto.RecruitmentPostRequest
import com.backend.domain.user.entity.SiteUser
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@DiscriminatorValue("recruitment")
open class RecruitmentPost : Post {
    lateinit var recruitmentClosingDate: ZonedDateTime
    var numOfApplicants: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    lateinit var recruitmentStatus: RecruitmentStatus

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    lateinit var jobPosting: JobPosting

    constructor(
        subject: String,
        content: String,
        category: Category,
        author: SiteUser,
        jobPostingId: Long,
        jobPostingRepository: JobPostingRepository
    ) : super(subject, content, category, author) {
        this.subject = subject
        this.content = content
        this.category = category
        this.author = author
        this.jobPosting = jobPostingRepository.findById(jobPostingId)
            .orElseThrow { throw IllegalArgumentException("존재하지 않는 JobPosting ID: $jobPostingId") }
    }

    fun updatePost(subject: String, content: String, numOfApplicants: Int) {
        super.updatePost(subject, content)
        this.numOfApplicants = numOfApplicants
    }

    fun updateRecruitmentStatus(recruitmentStatus: RecruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus
    }


}