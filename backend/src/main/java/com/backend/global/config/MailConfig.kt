package com.backend.global.config;

import com.backend.global.mail.util.EmailTemplateMaker
import com.backend.global.mail.util.TemplateMaker
import com.backend.global.mail.util.TemplateName
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver
import java.util.concurrent.ConcurrentHashMap

@Configuration
class MailConfig(
	@Value("\${mail.host}")
	val mailHost: String,

	@Value("\${mail.port}")
	val mailPort: Int,

	@Value("\${mail.username}")
	val mailUsername: String,

	@Value("\${mail.password}")
	val mailPassword: String,

	@Value("\${mail.properties.mail.smtp.auth}")
	val smtpAuth: Boolean,

	@Value("\${mail.properties.mail.smtp.starttls.enable}")
	val smtpStartTlsEnable: Boolean,

	@Value("\${mail.templates.path}")
	val templatesPath: String,

	@Value("\${mail.templates.recruitment-chat}")
	val recruitmentChat: String
) {
	@Bean
	fun mailSender(): JavaMailSender {
		val mailSender = JavaMailSenderImpl();
		mailSender.host = mailHost
		mailSender.port = mailPort
		mailSender.username = mailUsername
		mailSender.password = mailPassword
		val props = System.getProperties()
		props["mail.transport.protocol"] = "smtp"
		props["mail.smtp.starttls.enable"] = smtpStartTlsEnable
		mailSender.javaMailProperties = props
		return mailSender
	}

	@Bean
	fun emailTemplateMaker(): TemplateMaker {
		val templateNameMap: MutableMap<String, String> = ConcurrentHashMap()

		//각 템플릿 이름 Map에 저장
		templateNameMap[TemplateName.RECRUITMENT_CHAT.toString()] =  recruitmentChat

		val emailTemplateMaker = EmailTemplateMaker(
			thymeleafTemplateEngine(),
			templateNameMap
		)

		return emailTemplateMaker
	}

	@Bean
	fun thymeleafTemplateEngine(): SpringTemplateEngine {
		val templateEngine = SpringTemplateEngine()
		templateEngine.setTemplateResolver(thymeleafTemplateResolver())
		return templateEngine
	}

	@Bean
	fun thymeleafTemplateResolver(): ITemplateResolver {
		val templateResolver = ClassLoaderTemplateResolver()
		templateResolver.prefix = templatesPath
		templateResolver.suffix = ".html"
		templateResolver.characterEncoding = "UTF-8"
		templateResolver.setTemplateMode("HTML")
		return templateResolver
	}
}
