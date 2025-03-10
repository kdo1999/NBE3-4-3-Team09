package com.backend.global.mail.service;


import com.backend.global.mail.util.MailSender
import com.backend.global.mail.util.TemplateMaker
import com.backend.global.mail.util.TemplateName
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class MailServiceImpl(
    @Value("\${mail.chat_url}")
    private val chatUrl: String,
    private val templateMaker: TemplateMaker,
    private val mailSender: MailSender
) : MailService {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun sendDeliveryStartEmailAsync(to: List<String>, templateName: TemplateName, postId: Long) {
        scope.launch {
            sendDeliveryStartEmail(to, templateName, postId)
        }
    }

    private suspend fun sendDeliveryStartEmail(to: List<String>, templateName: TemplateName, postId: Long) {
        val titleBuilder = StringBuilder()
        val htmlParameterMap = mutableMapOf<String, String>()

        when (templateName) {
            TemplateName.RECRUITMENT_CHAT -> titleBuilder.append("[TEAM9] 모집 완료 안내 메일 입니다.")
        }

        val title = titleBuilder.toString()
        val chatUrlToString = "$chatUrl$postId"

        htmlParameterMap["chatUrl"] = chatUrlToString

        log.info { htmlParameterMap["chatUrl"] }

        val mimeMessage =
            templateMaker.create(mailSender.createMimeMessage(), to, title, templateName, htmlParameterMap)

        mailSender.send(mimeMessage)
    }

    @PreDestroy
    fun destroy() {
        scope.cancel()
    }
}
