package com.backend.domain.jobposting.entity;

import com.backend.domain.voter.entity.Voter
import jakarta.persistence.*
import java.time.ZonedDateTime

/**
 * JobPosting
 * <p>채용 공고 엔티티 입니다.</p>
 *
 * @author Kim Dong O
 */
@Entity
@Table(name = "job_posting")
class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_id")
    var id: Long? = null
        protected set

    @Column(nullable = false)
    lateinit var subject: String //제목
        protected set

    @Column(nullable = false)
    lateinit var url: String //url
        protected set

    @Column(nullable = false)
    lateinit var postDate: ZonedDateTime //작성 날짜
        protected set

    @Column(nullable = false)
    lateinit var openDate: ZonedDateTime //공개 날짜
        protected set

    @Column(nullable = false)
    lateinit var closeDate: ZonedDateTime //마감 날짜
        protected set

    @Column(nullable = false)
    lateinit var companyName: String
        //회사 이름
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var jobPostingStatus: JobPostingStatus //공고 상태
        protected set

    @Embedded
    @Column(nullable = false)
    lateinit var salary: Salary //연봉
        protected set

    @Column(name = "apply_cnt", nullable = false)
    var applyCnt: Long? = null //지원자 수
        protected set

    @Embedded
    @Column(nullable = false)
    lateinit var experienceLevel: ExperienceLevel //직무 경력
        protected set

    @Embedded
    @Column(nullable = false)
    lateinit var requireEducate: RequireEducate //학력
        protected set

    @Column(name = "job_id", nullable = false)
    var jobId: Long? = null
        protected set

    var companyLink: String? = null //회사 링크
        protected set

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "jobPosting")
    private var _jobPostingJobSkillList: MutableList<JobPostingJobSkill> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "jobPosting")
    private var _voterList: MutableList<Voter> = mutableListOf()

    val jobPostingJobSkillList: List<JobPostingJobSkill>
        get() = _jobPostingJobSkillList.toList()

    val voterList: List<Voter>
        get() = _voterList.toList()

    constructor(
        subject: String,
        url: String,
        postDate: ZonedDateTime,
        openDate: ZonedDateTime,
        closeDate: ZonedDateTime,
        companyName: String,
        jobPostingStatus: JobPostingStatus,
        salary: Salary,
        applyCnt: Long?,
        experienceLevel: ExperienceLevel,
        requireEducate: RequireEducate,
        jobId: Long?,
        companyLink: String?,
    ) {
        this.subject = subject
        this.url = url
        this.postDate = postDate
        this.openDate = openDate
        this.closeDate = closeDate
        this.companyName = companyName
        this.jobPostingStatus = jobPostingStatus
        this.salary = salary
        this.applyCnt = applyCnt
        this.experienceLevel = experienceLevel
        this.requireEducate = requireEducate
        this.jobId = jobId
        this.companyLink = companyLink
    }

    constructor(id: Long) {
        this.id = id
    }

    fun addJobPostingJobSkill(jobSkill: JobPostingJobSkill) {
        _jobPostingJobSkillList.add(jobSkill)
    }
}