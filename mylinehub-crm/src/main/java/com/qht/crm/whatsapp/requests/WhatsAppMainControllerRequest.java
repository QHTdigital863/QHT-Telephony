package com.qht.crm.whatsapp.requests;

import java.util.Date;
import java.util.List;

import com.qht.crm.whatsapp.dto.WhatsAppManagementEmployeeDto;

import lombok.Data;

@Data
public class WhatsAppMainControllerRequest {

	Long id;
	Date dayUpdated;
	String phoneNumber;
	String typeOfReport;
	String category;
	String organization;
	Long whatsappProjectId;
	Long adminEmployeeID;
	boolean active;
	List<WhatsAppManagementEmployeeDto> employeeExtensionAccessList;
}
