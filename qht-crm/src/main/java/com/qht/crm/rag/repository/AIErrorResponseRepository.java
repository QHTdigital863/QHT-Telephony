package com.qht.crm.rag.repository;

import com.qht.crm.rag.model.AIError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIErrorResponseRepository extends JpaRepository<AIError, Long> {
}