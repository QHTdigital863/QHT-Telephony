package com.qht.crm.gst.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.gst.entity.RegisteredGST;


@Repository
public interface RegisterGstRepository  extends JpaRepository<RegisteredGST, Long> {

	RegisteredGST getByBusinessId(String businessId);
}
