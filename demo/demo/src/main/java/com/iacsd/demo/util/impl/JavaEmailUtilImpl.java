package com.iacsd.demo.util.impl;

import com.iacsd.demo.util.EmailUtil;
import com.iacsd.demo.util.dto.EmailNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@Slf4j
public class JavaEmailUtilImpl implements EmailUtil {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${app.email.from}")
    private String from;

    @Override
    public void sendEmail(EmailNotification notification) {
        log.debug("sending email, subject : {}, to : {}", notification.getSubject(), notification.getTo());
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(notification.getTo());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getBody(), true);
            helper.setFrom(from);
            if (!CollectionUtils.isEmpty(notification.getAttachments())) {
                log.debug("No of attachment :: {}", notification.getAttachments().size());
                for(Map.Entry<String, byte[]> att : notification.getAttachments().entrySet()) {
                    helper.addAttachment(att.getKey(), new ByteArrayResource(att.getValue()));
                }
            }
            emailSender.send(message);
            log.debug("email sent successfully");
        } catch (MessagingException e) {
            log.error("error while sending email",e);
        }
    }
}
