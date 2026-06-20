package com.qht.aiemail.service;

import com.qht.aiemail.model.OrganizationEmailAccount;

/**
 * Low-level SMTP sender that uses the per-account SMTP settings.
 */
public interface SmtpSenderService {

    void sendReply(OrganizationEmailAccount account,
                   String to,
                   String subject,
                   String bodyText);
}
