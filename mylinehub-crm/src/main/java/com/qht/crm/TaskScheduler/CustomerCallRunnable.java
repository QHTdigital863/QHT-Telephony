package com.qht.crm.TaskScheduler;

import java.util.Date;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.qht.crm.ami.TaskScheduler.NoDatabaseCallRunnable;
import com.qht.crm.ami.service.notificaton.EmployeeCallErrorNotificationService;
import com.qht.crm.data.EmployeeDataAndState;
import com.qht.crm.data.OrganizationData;
import com.qht.crm.data.dto.CallToExtensionDTO;
import com.qht.crm.entity.Employee;
import com.qht.crm.entity.Organization;
import com.qht.crm.entity.dto.CustomerDTO;
import com.qht.crm.entity.dto.EmployeeDataAndStateDTO;
import com.qht.crm.repository.NotificationRepository;
import com.qht.crm.service.CustomerService;
import com.qht.crm.service.SendScheduleCallDetailToExtensionService;
import com.qht.crm.TaskSchedule.Service.DeleteScheduleJobFromDatabaseService;

import lombok.Data;

@Data
public class CustomerCallRunnable  implements Runnable{

	private String jobId;
	private String phoneNumber;
	private String fromExtension;
	private String fromPhoneNumber;
	boolean isCallOnMobile;
	private String callType;
    private String organization;
    private String domain;
    private ApplicationContext applicationContext;
    private NotificationRepository notificationRepository;
    private String context;
    private int priority;
    private Long timeOut;
	private String firstName;
	private String protocol;
	private String phoneTrunk;
	boolean useSecondaryLine;
	String secondDomain;

    @Override
    public void run() {
    	System.out.println("CustomerCallRunnable started for jobId: " + jobId + ", phoneNumber: " + phoneNumber);

    	EmployeeCallErrorNotificationService employeeCallErrorNotificationService  = applicationContext.getBean(EmployeeCallErrorNotificationService.class);
    	SendScheduleCallDetailToExtensionService sendScheduleCallDetailToExtensionService =  applicationContext.getBean(SendScheduleCallDetailToExtensionService.class);
    	CustomerService customerService  = applicationContext.getBean(CustomerService.class);
    	DeleteScheduleJobFromDatabaseService deleteScheduleJobFromDatabaseService = applicationContext.getBean(DeleteScheduleJobFromDatabaseService.class);

    	try {
    		
    		Organization organizationObject = null;
    		Map<String, Organization> organizationMap =
    				OrganizationData.workWithAllOrganizationData(organization, null, "get-one", null);
    		if (organizationMap != null) {
    			organizationObject = organizationMap.get(organization);
    		} else {
    			new Exception("Organization now found for emp");
    		}
    		
    		System.out.println("Preparing to originate call...");
    		NoDatabaseCallRunnable runnable = new NoDatabaseCallRunnable();
    		runnable.setJobId(jobId);
    		runnable.setPhoneNumber(phoneNumber);
    		runnable.setFromExtension(fromExtension);
    		runnable.setCallType("Outbound");
    		runnable.setOrganization(organization);
    		runnable.setDomain(domain);
    		runnable.setContext(context);
    		runnable.setProtocol(protocol);
    		runnable.setPhoneTrunk(phoneTrunk);
    		runnable.setPriority(1);
    		runnable.setTimeOut(30000L);
    		runnable.setFirstName(firstName);
    		runnable.setApplicationContext(applicationContext);
    		runnable.setNotificationRepository(notificationRepository);
			runnable.setUseSecondaryLine(useSecondaryLine);
			runnable.setCallOnMobile(isCallOnMobile);
			runnable.setFromPhoneNumber(fromPhoneNumber);
			runnable.setSecondDomain(secondDomain);
			runnable.setPridictive(true);
			runnable.setSimSelector(organizationObject.getSimSelector());
	        runnable.setSimSelectorRequired(organizationObject.isSimSelectorRequired());
			runnable.run();

			System.out.println("Call originated successfully for phoneNumber: " + phoneNumber);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error while originating scheduled call for jobId: " + jobId);
    		e.printStackTrace();
    		//In case of error send a notification to extension about call
    		try {
    			System.out.println("Sending error notification to employee extension: " + fromExtension);
    			employeeCallErrorNotificationService.sendEmployeeCallErrorNotifications(fromExtension, firstName, fromPhoneNumber, organization, domain, notificationRepository);
			} catch (Exception e1) {
				System.out.println("Failed to send error notification for jobId: " + jobId);
				e1.printStackTrace();
			}
    	}	

    	try {
    		System.out.println("Retrieving customer data for phoneNumber: " + phoneNumber);
    		//Find customer
    		CustomerDTO customerDTO = customerService.getByPhoneNumberAndOrganization(phoneNumber, organization);
    		if(customerDTO == null) {
    			System.out.println("No customer found with phoneNumber: " + phoneNumber + " for organization: " + organization);
    		} else {
    			System.out.println("Customer found: " + customerDTO);
    		}

    		Map<String,EmployeeDataAndStateDTO> allEmployeeDataAndState = EmployeeDataAndState.workOnAllEmployeeDataAndState(fromExtension, null, "get-one");
    		EmployeeDataAndStateDTO employeeDataAndStateDTO= null;
    		if(allEmployeeDataAndState != null)
    		{
    			employeeDataAndStateDTO = allEmployeeDataAndState.get(fromExtension);
    		} 

    		Employee employee = null;

    		if(employeeDataAndStateDTO != null)
    		{
    			employee = employeeDataAndStateDTO.getEmployee();
    		} else {
    			System.out.println("No employee data and state found for fromExtension: " + fromExtension);
    		}

    		if(employee!=null) {
	    		//Send Notification
				CallToExtensionDTO callToExtensionDTO = new CallToExtensionDTO();
				callToExtensionDTO.setCurrentDate(new Date());
				callToExtensionDTO.setAutodialertype("");
				callToExtensionDTO.setCampginId(0L);
				callToExtensionDTO.setCampginName("");
				callToExtensionDTO.setRemindercalling(false);
				callToExtensionDTO.setCustomer(customerDTO);
				
				sendScheduleCallDetailToExtensionService.sendMessageToExtension(callToExtensionDTO, employee);
				System.out.println("Notification sent to extension for fromExtension: " + fromExtension);
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error during customer retrieval or notification sending for jobId: " + jobId);
    		e.printStackTrace();
    	}

    	try {
    		System.out.println("Deleting schedule job from DB for jobId: " + jobId);
    		deleteScheduleJobFromDatabaseService.deleteScheduleJobFromDatabaseIfExecuted(jobId);
    		System.out.println("Deleted schedule job from DB for jobId: " + jobId);
    	} catch(Exception e) {
    		System.out.println("Failed to delete schedule job from DB for jobId: " + jobId);
    		e.printStackTrace();
    	}
    }
	
}
