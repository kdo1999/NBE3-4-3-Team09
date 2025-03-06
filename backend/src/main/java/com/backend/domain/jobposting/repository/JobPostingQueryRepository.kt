package com.backend.domain.jobposting.repository

import com.backend.domain.jobposting.dto.JobPostingDetailResponse
import com.backend.domain.jobposting.dto.JobPostingPageResponse
import com.backend.domain.jobposting.dto.QJobPostingDetailResponse
import com.backend.domain.jobposting.dto.QJobPostingPageResponse
import com.backend.domain.jobposting.entity.QJobPosting.jobPosting
import com.backend.domain.jobposting.entity.QJobPostingJobSkill.jobPostingJobSkill
import com.backend.domain.jobposting.util.JobPostingSearchCondition
import com.backend.domain.jobskill.dto.QJobSkillResponse
import com.backend.domain.voter.entity.QVoter.voter
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import java.util.*

@Repository
class JobPostingQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findAll(
        jobPostingSearchCondition: JobPostingSearchCondition,
        pageable: Pageable
    ): Page<JobPostingPageResponse> {
        val content = queryFactory.select(
            QJobPostingPageResponse(
                jobPosting.id, jobPosting.subject,
                jobPosting.openDate, jobPosting.closeDate, jobPosting.experienceLevel,
                jobPosting.requireEducate, jobPosting.jobPostingStatus, jobPosting.salary,
                jobPosting.applyCnt
            )
        )
            .from(jobPosting)
            .where(
                getSubjectContains(jobPostingSearchCondition.kw),
                getExperienceLevelEq(jobPostingSearchCondition.experienceLevel),
                getRequireEducateCode(jobPostingSearchCondition.requireEducateCode),
                getSalaryCodeBetween(jobPostingSearchCondition.salaryCode)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory.select(jobPosting.count())
            .from(jobPosting)
            .where(
                getSubjectContains(jobPostingSearchCondition.kw),
                getExperienceLevelEq(jobPostingSearchCondition.experienceLevel),
                getRequireEducateCode(jobPostingSearchCondition.requireEducateCode),
                getSalaryCodeBetween(jobPostingSearchCondition.salaryCode)
            )

        return PageableExecutionUtils.getPage(content, pageable) { countQuery.fetchOne()!! }
    }

    fun findDetailById(jobPostingId: Long, siteUserId: Long): Optional<JobPostingDetailResponse> {
        val siteUserVoted = JPAExpressions
            .selectOne()
            .from(voter)
            .leftJoin(voter.jobPosting)
            .where(voter.jobPosting.id.eq(jobPostingId))
            .exists()

        val jobSkillResponses = queryFactory.select(
            QJobSkillResponse(
                jobPostingJobSkill.jobSkill.name,
                jobPostingJobSkill.jobSkill.code
            )
        )
            .from(jobPostingJobSkill)
            .leftJoin(jobPostingJobSkill.jobPosting)
            .leftJoin(jobPostingJobSkill.jobSkill)
            .where(jobPostingJobSkill.jobPosting.id.eq(jobPostingId))
            .fetch()

        val jobPostingDetailResponse = queryFactory.selectDistinct(
            QJobPostingDetailResponse(
                jobPosting.id, jobPosting.subject, jobPosting.url,
                jobPosting.postDate, jobPosting.openDate, jobPosting.closeDate,
                jobPosting.companyName, jobPosting.companyLink, jobPosting.experienceLevel,
                jobPosting.requireEducate, jobPosting.jobPostingStatus, jobPosting.salary,
                Expressions.constant(jobSkillResponses),
                jobPosting.applyCnt, voter.countDistinct(), siteUserVoted
            )
        )
            .from(jobPosting)
            .leftJoin(jobPosting._voterList, voter)
            .groupBy(
                jobPosting.id, jobPosting.postDate, jobPosting.openDate, jobPosting.closeDate,
                jobPosting.companyName, jobPosting.companyLink, jobPosting.experienceLevel,
                jobPosting.requireEducate, jobPosting.jobPostingStatus, jobPosting.salary,
                jobPosting.applyCnt
            )
            .where(jobPosting.id.eq(jobPostingId))
            .fetchOne()

        return Optional.ofNullable(jobPostingDetailResponse)
    }

    fun findAllVoter(
        jobPostingSearchCondition: JobPostingSearchCondition,
        siteUserId: Long,
        pageable: Pageable
    ): Page<JobPostingPageResponse> {
        val content = queryFactory.select(
            QJobPostingPageResponse(
                jobPosting.id, jobPosting.subject,
                jobPosting.openDate, jobPosting.closeDate, jobPosting.experienceLevel,
                jobPosting.requireEducate, jobPosting.jobPostingStatus, jobPosting.salary,
                jobPosting.applyCnt
            )
        )
            .from(jobPosting)
            .leftJoin(jobPosting._voterList, voter)
            .where(
                getSubjectContains(jobPostingSearchCondition.kw),
                getExperienceLevelEq(jobPostingSearchCondition.experienceLevel),
                getRequireEducateCode(jobPostingSearchCondition.requireEducateCode),
                getSalaryCodeBetween(jobPostingSearchCondition.salaryCode),
                getVoterSiteUserEq(siteUserId)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory.select(jobPosting.count())
            .from(jobPosting)
            .where(
                getSubjectContains(jobPostingSearchCondition.kw),
                getExperienceLevelEq(jobPostingSearchCondition.experienceLevel),
                getRequireEducateCode(jobPostingSearchCondition.requireEducateCode),
                getSalaryCodeBetween(jobPostingSearchCondition.salaryCode),
                getVoterSiteUserEq(siteUserId)
            )

        return PageableExecutionUtils.getPage(content, pageable) { countQuery.fetchOne()!! }
    }

    private fun getOrderBy(jobPostingSearchCondition: JobPostingSearchCondition): OrderSpecifier<*> {
        val queryOrder = if (Order.ASC.toString().equals(jobPostingSearchCondition.order, ignoreCase = true))
            Order.ASC
        else
            Order.DESC

        val fieldMap = mapOf(
            "applyCnt" to jobPosting.applyCnt,
            "openDate" to jobPosting.openDate
        )

        val sortField = if (StringUtils.hasText(jobPostingSearchCondition.sort) &&
            fieldMap.containsKey(jobPostingSearchCondition.sort)
        ) {
            fieldMap[jobPostingSearchCondition.sort]
        } else {
            jobPosting.openDate
        }

        return OrderSpecifier(queryOrder, sortField!!)
    }

    private fun getSalaryCodeBetween(salaryCode: Int?): BooleanExpression? {
        return when {
            salaryCode == null -> null
            salaryCode == 0 || salaryCode >= 99 -> jobPosting.salary.code.eq(salaryCode)
            else -> jobPosting.salary.code.between(salaryCode, 22)
        }
    }

    private fun getRequireEducateCode(requireEducateCode: Int?): BooleanExpression? {
        return requireEducateCode?.let { jobPosting.requireEducate.code.eq(it) }
    }

    private fun getExperienceLevelEq(experienceLevel: Int?): BooleanExpression? {
        return experienceLevel?.let { jobPosting.experienceLevel.code.eq(it) }
    }

    private fun getSubjectContains(kw: String?): BooleanExpression? {
        return kw?.takeIf { StringUtils.hasText(it) }?.let { jobPosting.subject.contains(it) }
    }

    private fun getVoterSiteUserEq(siteUserId: Long): BooleanExpression {
        return voter.siteUser.id.eq(siteUserId)
    }
}