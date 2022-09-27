package com.iacsd.demo.util;


import com.iacsd.demo.util.dto.EmailNotification;

public interface EmailUtil {
    void sendEmail(EmailNotification notification);
}
