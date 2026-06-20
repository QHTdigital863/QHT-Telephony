package com.qht.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.entity.SystemConfig;



@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
	
	List<SystemConfig> findAllByOrganization(String organization);
   
}