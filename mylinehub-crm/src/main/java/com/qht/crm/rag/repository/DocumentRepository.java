package com.qht.crm.rag.repository;

import com.qht.crm.rag.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByFileHashAndOrganization(String fileHash, String organization);
    Optional<Document> findByOriginalFilenameAndMimeTypeAndOrganization(String originalFilename,String mimeType, String organization);
}
