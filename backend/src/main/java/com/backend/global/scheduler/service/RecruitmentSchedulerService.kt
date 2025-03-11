package com.backend.global.scheduler.service

import com.backend.domain.post.repository.recruitment.RecruitmentPostRepository
import com.backend.domain.recruitmentUser.repository.RecruitmentUserRepository
import com.backend.global.mail.service.MailService
import com.backend.global.mail.util.TemplateName
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

private val log = KotlinLogging.logger {}

@Service
class RecruitmentSchedulerService(
    private val mailService: MailService,
    private val recruitmentPostRepository: RecruitmentPostRepository,
    private val recruitmentUserRepository: RecruitmentUserRepository,
) {

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    @Transactional
    fun runProcess() {
        updateRecruitmentStatus()
    }
    
    private fun updateRecruitmentStatus() {
        // 오늘 이전이며 모집 중인 게시글 조회
        val recruitmentClosingDate = ZonedDateTime.now()

        val recruitmentPostList =
            recruitmentPostRepository.findPostByRecruitmentClosingDateAndRecruitmentStatus(recruitmentClosingDate)

        val recruitPostIdList = recruitmentPostList.map { it.postId!! }.toList()

        //모집 게시글 상태 변경
        recruitmentPostRepository.updatePostByRecruitmentClosingDate(recruitmentClosingDate, recruitPostIdList)

        // 모집 중인 게시글로 RecruitmentUser를 빼온다.
        recruitmentPostList.forEach {
            val findRecruitmentUserList =
                recruitmentUserRepository.findAcceptedRecruitmentsForClosedPost(it.postId!!)

            val findRecruitmentUserEmailList = findRecruitmentUserList.map { it.siteUser.email }.toList()

            mailService.sendRecruitmentEmailAsync(
                findRecruitmentUserEmailList,
                TemplateName.RECRUITMENT_END,
                it.postId!!
            )
        }
    }
}