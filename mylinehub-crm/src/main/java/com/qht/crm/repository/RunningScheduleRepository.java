package com.qht.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.entity.RunningSchedule;

@Repository
public interface RunningScheduleRepository extends JpaRepository<RunningSchedule, Long> {
		
	//No function is required here
	
    List<RunningSchedule> findAllByOrganizationOrderByIdAsc(String organization);
    List<RunningSchedule> findAllByJobId(String jobId);
}


