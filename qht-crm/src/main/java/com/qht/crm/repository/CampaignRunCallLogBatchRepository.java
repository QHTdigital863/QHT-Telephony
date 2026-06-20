package com.qht.crm.repository;

import com.qht.crm.data.StartedCampaignData;
import java.util.List;

public interface CampaignRunCallLogBatchRepository {
    int batchUpsertCallLogs(List<StartedCampaignData.CampaignRunCallLogMem> rows, Long fallbackRunId, Long campaignId);
}
