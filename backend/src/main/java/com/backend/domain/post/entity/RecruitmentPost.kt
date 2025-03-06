package com.backend.domain.post.entity

import com.backend.domain.category.entity.Category
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.post.dto.RecruitmentPostRequest
import com.backend.domain.user.entity.SiteUser
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@DiscriminatorValue("recruitment")
open class RecruitmentPost (

    subject: String,
    content: String,
    category: Category,
    author: SiteUser,
    var recruitmentClosingDate: ZonedDateTime? = null,
    var numOfApplicants: Int? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var recruitmentStatus: RecruitmentStatus? = null,
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    var jobPosting: JobPosting? = null
) : Post(subject, content, category, author) {

    fun updatePost(subject: String, content: String, numOfApplicants: Int?){
        super.updatePost(subject, content)
        this.numOfApplicants = numOfApplicants
    }

    fun updateRecruitmentStatus(recruitmentStatus: RecruitmentStatus){
        this.recruitmentStatus = recruitmentStatus
    }

    constructor(request: RecruitmentPostRequest, category: Category, author: SiteUser, jobPosting: JobPosting) :
            this(
                subject = request.subject,
                content = request.content,
                category = category,
                author = author,
                recruitmentClosingDate = request.recruitmentClosingDate,
                numOfApplicants = request.numOfApplicants,
                jobPosting = jobPosting
            )
}