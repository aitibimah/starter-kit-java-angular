package com.yourcompany.project.service;

import jakarta.mail.MessagingException;
import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendHtmlMessage(String to, String subject, String templateName, Map<String, Object> templateModel)
            throws MessagingException;

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
            throws MessagingException;
}