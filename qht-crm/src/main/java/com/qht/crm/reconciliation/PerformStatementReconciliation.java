package com.qht.crm.reconciliation;

import java.io.InputStream;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.qht.crm.entity.Employee;
import com.qht.crm.repository.ErrorRepository;
import com.qht.crm.service.DepartmentService;
import com.qht.crm.service.EmployeeService;

public class PerformStatementReconciliation {
	
	  public List<Employee> loadExcelToRawDataMap(EmployeeService employeeService,DepartmentService departmentService,InputStream is,String email,String organization,BCryptPasswordEncoder passwordEncoder, ErrorRepository errorRepository) throws Exception {
		 
		 return null;
	  }
}


