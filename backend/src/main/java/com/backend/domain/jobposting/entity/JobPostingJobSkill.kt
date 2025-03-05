package com.backend.domain.jobposting.entity;

import com.backend.domain.jobskill.entity.JobSkill
import jakarta.persistence.*

@Entity
@Table(name = "job_posting_job_skill")
class JobPostingJobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
    lateinit var jobPosting: JobPosting
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_skill_id")
    lateinit var jobSkill: JobSkill
        protected set

    constructor(jobPosting: JobPosting, jobSkill: JobSkill) {
        this.jobPosting = jobPosting
        this.jobSkill = jobSkill
    }
}
