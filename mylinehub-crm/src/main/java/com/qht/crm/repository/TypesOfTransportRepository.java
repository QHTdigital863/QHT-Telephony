package com.qht.crm.repository;

import com.qht.crm.enums.MODE_OF_TRANSPORT_CODE;
import com.qht.crm.entity.TypesOfTransport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesOfTransportRepository extends JpaRepository<TypesOfTransport, MODE_OF_TRANSPORT_CODE> {

	List<TypesOfTransport> findAllByOrganization(String organization);
}
