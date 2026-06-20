package com.qht.aiemail.service;

import com.qht.aiemail.dto.EmailReportDTO;

/**
 * Sends email-level reporting (for analytics / CRM) to QHT Clinic backend.
 */
public interface EmailReportingService {

    void reportEmail(EmailReportDTO dto);
}
