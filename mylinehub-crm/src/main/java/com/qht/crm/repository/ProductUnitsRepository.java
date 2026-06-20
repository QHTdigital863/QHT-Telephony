package com.qht.crm.repository;

import com.qht.crm.entity.ProductUnits;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUnitsRepository extends JpaRepository<ProductUnits, Long> {
	
	List<ProductUnits> findAllByOrganization(String organization);
}
