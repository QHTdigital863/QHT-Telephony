package com.qht.crm.rag.repository;

import com.qht.crm.rag.model.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
}
