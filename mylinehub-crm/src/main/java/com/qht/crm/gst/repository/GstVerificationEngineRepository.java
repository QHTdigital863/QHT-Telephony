package com.qht.crm.gst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.gst.entity.GstVerificationEngine;

@Repository
public interface GstVerificationEngineRepository extends JpaRepository<GstVerificationEngine, Long> {
	GstVerificationEngine getGstVerificationEngineByEngineName(String engineName);
	List<GstVerificationEngine> getGstVerificationEngineByActive(boolean active);
}
