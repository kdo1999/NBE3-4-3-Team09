package com.backend.global.mail.util;

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.util.concurrent.ConcurrentHashMap

/**
 * TemplateMaker 구현체 입니다.
 * <p>이메일 템플릿을 만들어 반환합니다.</p>
 *
 * @author : Kim Dong O
 */
private val log = KotlinLogging.logger {}

class EmailTemplateMaker(
    private val templateEngine: SpringTemplateEngine,
	private val templateNameMap: MutableMap<String, String> = ConcurrentHashMap()
) : TemplateMaker {

    override fun create(
        newMimeMessage: MimeMessage,
        usernameList: List<String>,
        title: String,
        templateName: TemplateName,
        htmlParameterMap: Map<String, String>
    ): MimeMessage {
        try {
			val helper = MimeMessageHelper(newMimeMessage, true, "UTF-8")

            val context = Context();

            //파라미터 값 설정
            htmlParameterMap.forEach(context::setVariable);

            val emailArray = usernameList.toTypedArray()

            val processedHtmlContent = templateEngine.process(
				templateNameMap[templateName.toString()],
				context);
			log.info { "processedHtmlContent = $processedHtmlContent" }

            helper.setTo(emailArray);
            helper.setSubject(title);
            helper.setText(processedHtmlContent, true);

        } catch (e: MessagingException) {
            log.error{ "EmailTemplateMaker: $e.message" }
        }

        return newMimeMessage;
    }
}
