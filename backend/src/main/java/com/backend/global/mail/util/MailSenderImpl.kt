package com.backend.global.mail.util;

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

/**
 * MailSender
 * <p>MailSender 구현체 입니다.</p>
 * @author Kim Dong O
 */
@Component
class MailSenderImpl(private val javaMailSender: JavaMailSender): MailSender {

	override fun send(mimeMessage: MimeMessage) = javaMailSender.send(mimeMessage)

	override fun createMimeMessage(): MimeMessage = javaMailSender.createMimeMessage()
}
