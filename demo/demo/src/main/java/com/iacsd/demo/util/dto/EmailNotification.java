package com.iacsd.demo.util.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
@NoArgsConstructor
public class EmailNotification {
    private String subject;
    private String body;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Map<String, byte[]> attachments;

    public static EmailNotification from(List<Recipient> recipients, String content, String subject, Map<String, byte[]> attachments) {
        EmailNotification notification=new EmailNotification();
        Map<RecipientType, List<String>> recipient = recipients.stream().collect(Collectors.groupingBy(Recipient::getType, Collectors.mapping(Recipient::getRecipient, Collectors.toList())));
        notification.setTo(recipient.get(RecipientType.TO).toArray(new String[]{}));
        if (recipient.containsKey(RecipientType.CC)) {
            notification.setCc(recipient.get(RecipientType.CC).toArray(new String[]{}));
        }
        if (recipient.containsKey(RecipientType.BCC)) {
            notification.setBcc(recipient.get(RecipientType.BCC).toArray(new String[]{}));
        }
        notification.setBody(content);
        notification.setSubject(subject);
        notification.setAttachments(attachments);
        log.debug("Notification :: TO -> {}, CC -> {}, BCC -> {}", notification.getTo(), notification.getCc(), notification.getBcc());
        return notification;
    }
}
