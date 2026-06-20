package com.qht.crm.rag.service;

import com.qht.crm.rag.dto.IngestResponse;
import com.qht.crm.rag.dto.IngestRequest;

public interface IngestService {
    IngestResponse ingest(IngestResponse request) throws Exception;

    IngestResponse deleteByHashes(String organization, java.util.List<String> fileHashes);
    
    IngestResponse ingestUrl(IngestRequest req);
}
