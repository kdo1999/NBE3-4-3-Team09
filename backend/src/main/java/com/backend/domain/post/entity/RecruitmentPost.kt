package com.backend.domain.post.entity

import com.backend.domain.category.entity.Category
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.user.entity.SiteUser
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@DiscriminatorValue("recruitment")
open class RecruitmentPost (

    postId: Long? = null,
    subject: String,
    content: String,
    category: Category,
    author: SiteUser,

    private var recruitmentClosingDate: ZonedDateTime?,
    private var numOfApplicants: Int?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var recruitmentStatus: RecruitmentStatus?,

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    var jobPosting: JobPosting?
) : Post(postId, subject, content, category, author) {

    fun updatePost(subject: String, content: String, numOfApplicants: Int?){
        super.updatePost(subject, content)
        this.numOfApplicants = numOfApplicants
    }

    fun updateRecruitmentStatus(recruitmentStatus: RecruitmentStatus){
        this.recruitmentStatus = recruitmentStatus
    }
}