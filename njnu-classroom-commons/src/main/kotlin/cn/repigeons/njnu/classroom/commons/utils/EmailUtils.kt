package cn.repigeons.njnu.classroom.commons.utils

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File

@RefreshScope
@Configuration
private class EmailConfig(
    val mailSender: JavaMailSender,
) : InitializingBean {
    @Value("\${spring.mail.username}")
    var username: String = ""

    @Value("\${spring.mail.receivers:}")
    val receivers: Array<String> = arrayOf()
    override fun afterPropertiesSet() {
        emailConfig = this
    }
}

private lateinit var emailConfig: EmailConfig

object EmailUtils {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 发送邮件
     */
    fun send(
        nickname: String? = null,
        subject: String,
        content: String,
        receivers: Array<String> = emailConfig.receivers,
        ccReceivers: Array<String>? = null,
        html: Boolean = false
    ) = mono {
        logger.info("发送邮件：{},{},{},{}", subject, content, receivers, ccReceivers)
        val mimeMessage = emailConfig.mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, false)
        // 发件人
        if (nickname == null) helper.setFrom(emailConfig.username)
        else helper.setFrom(emailConfig.username, nickname)
        // 收件人
        helper.setTo(receivers)
        // 抄送
        if (!ccReceivers.isNullOrEmpty()) helper.setCc(ccReceivers)
        // 邮件主题
        helper.setSubject(subject)
        // 邮件内容
        helper.setText(content, html)
        emailConfig.mailSender.send(mimeMessage)
        logger.info("发送邮件成功")
    }

    /**
     * 附件邮件
     */
    fun sendFile(
        nickname: String? = null,
        subject: String,
        content: String,
        receivers: Array<String> = emailConfig.receivers,
        vararg attachments: File
    ) = mono {
        logger.info("发送邮件：{},{},{},{}", subject, content, receivers, attachments)
        val mimeMessage = emailConfig.mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true)
        // 发件人
        if (nickname == null) helper.setFrom(emailConfig.username)
        else helper.setFrom(emailConfig.username, nickname)
        // 收件人
        helper.setTo(receivers)
        // 邮件主题
        helper.setSubject(subject)
        // 邮件内容
        helper.setText(content, true)
        attachments.forEach { file ->
            helper.addAttachment(file.name, file)
        }
        emailConfig.mailSender.send(mimeMessage)
        logger.info("发送邮件成功")
    }

    suspend fun sendAndAwait(
        nickname: String? = null,
        subject: String,
        content: String,
        receivers: Array<String> = emailConfig.receivers,
        ccReceivers: Array<String>? = null,
        html: Boolean = false
    ) = send(nickname, subject, content, receivers, ccReceivers, html).awaitSingle()

    suspend fun sendFileAndAwait(
        nickname: String? = null,
        subject: String,
        content: String,
        receivers: Array<String> = emailConfig.receivers,
        vararg attachments: File
    ) = sendFile(nickname, subject, content, receivers, *attachments).awaitSingle()
}