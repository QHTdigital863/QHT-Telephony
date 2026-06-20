package com.qht.crm.entity.dto;

import com.qht.crm.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkUploadEmployeeDto {

	String actualPassword;
	Employee employee;
	String parentOrg;
	String reason;

}
