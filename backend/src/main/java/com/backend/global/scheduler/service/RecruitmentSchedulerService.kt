package com.backend.global.scheduler.service

import com.backend.domain.post.repository.recruitment.RecruitmentPostRepository
import com.backend.global.mail.service.MailService
import com.backend.global.mail.util.TemplateName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecruitmentSchedulerService(
    private val mailService: MailService,
    private val recruitmentPostRepository: RecruitmentPostRepository,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    @Transactional
    fun runProcess() {
        //모집 게시판 벌크 수정
    }
    
    private fun updateRecruitmentStatus() {


        //메일 발송
        val toList = listOf("kdo_1999@naver.com")
        val postId = 1L

        //메일 서비스 안에서 비동기 처리
        mailService.sendRecruitmentEmailAsync(toList, TemplateName.RECRUITMENT_END, postId)
    }
}