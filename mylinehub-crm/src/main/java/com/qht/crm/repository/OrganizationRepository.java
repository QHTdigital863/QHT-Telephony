package com.qht.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Organization findByOrganization(String organization);
	Organization findByBusinessIdentificationNumber(String businessIdentificationNumber);
	List<Organization> findAllByIdIn(List<Long> ids);
}