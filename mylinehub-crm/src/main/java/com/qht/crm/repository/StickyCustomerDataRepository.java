package com.qht.crm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.entity.Customers;
import com.qht.crm.entity.StickyCustomerData;

@Repository
public interface StickyCustomerDataRepository extends JpaRepository<StickyCustomerData, Long> {
	
	
	StickyCustomerData findFirstByCustomerAndOrganizationOrderByCreatedDateDesc(Customers customer,String organization);
	
}
