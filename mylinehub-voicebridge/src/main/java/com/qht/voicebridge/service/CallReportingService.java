package com.qht.voicebridge.service;

import com.qht.voicebridge.billing.CallBillingInfo;
import com.qht.voicebridge.session.CallSession;

public interface CallReportingService {

    /** Old behavior - still needed for backward compatibility */
    void reportCall(CallBillingInfo info);

    /** New behavior - used for CRM + CDR posting */
    void reportCall(CallSession session, CallBillingInfo info);
}
