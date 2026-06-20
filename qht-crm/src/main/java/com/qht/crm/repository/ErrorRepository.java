package com.qht.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.entity.Errors;

@Repository
public interface ErrorRepository extends JpaRepository<Errors, Long> {

	List<Errors> findAllByOrganization(String organization);
	
}