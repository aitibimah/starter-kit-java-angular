package com.yourcompany.project.service.impl;

import com.yourcompany.project.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final String fromEmail;

    public EmailServiceImpl(
            JavaMailSender emailSender,
            TemplateEngine templateEngine,
            @Value("${spring.mail.username}") String fromEmail) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String templateName, Map<String, Object> templateModel)
            throws MessagingException {
        Context context = new Context();
        templateModel.forEach(context::setVariable);

        String htmlContent = templateEngine.process(templateName, context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("Attachment", new java.io.File(pathToAttachment));

        emailSender.send(message);
    }
}
