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
        request: RecruitmentPostRequest, category: Category, author: SiteUser,
        jobPostingId: Long, jobPostingRepository: JobPostingRepository
    ) : super(request.subject, request.content, category, author) {
        this.recruitmentClosingDate = recruitmentClosingDate
        this.numOfApplicants = numOfApplicants
        this.recruitmentStatus = recruitmentStatus

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