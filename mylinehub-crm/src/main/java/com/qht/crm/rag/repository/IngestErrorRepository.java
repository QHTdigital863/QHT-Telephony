package com.qht.crm.rag.repository;

import com.qht.crm.rag.model.IngestError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngestErrorRepository extends JpaRepository<IngestError, Long> {
}
