package com.qht.crm.ami.TaskScheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.qht.crm.ami.autodialer.AutodialerReinitiateAndFunctionService;
import com.qht.crm.ami.autodialer.LoopInToDialOrSendMessage;
import com.qht.crm.data.EmployeeDataAndState;
import com.qht.crm.data.TrackedSchduledJobs;
import com.qht.crm.entity.Campaign;
import com.qht.crm.entity.Employee;
import com.qht.crm.entity.dto.EmployeeDataAndStateDTO;
import com.qht.crm.enums.AUTODIALER_TYPE;
import com.qht.crm.service.SchedulerService;
import com.qht.crm.utils.LoggerUtils;

import io.jsonwebtoken.io.IOException;
import lombok.Data;

@Data
public class DialAutomateCallCronRunnable  implements Runnable{
	
	private String jobId;
	private Employee employee;
	private Campaign campaign;
	private ApplicationContext applicationContext;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("DialAutomateCallCronRunnable");
		
		try {
			
			SchedulerService schedulerService  = applicationContext.getBean(SchedulerService.class);
			AutodialerReinitiateAndFunctionService autodialerReinitiateAndFunctionService = applicationContext.getBean(AutodialerReinitiateAndFunctionService.class);
			
			Map<String, EmployeeDataAndStateDTO> allEmployeeDataAndState = EmployeeDataAndState.workOnAllEmployeeDataAndState(employee.getExtension(), null, "get-one");
			EmployeeDataAndStateDTO employeeDataAndStateDTO= null;
			if(allEmployeeDataAndState != null)
			{
				employeeDataAndStateDTO = allEmployeeDataAndState.get(employee.getExtension());
			}

			if(employeeDataAndStateDTO!=null)
				System.out.println("employeeDataAndStateDTO.getLastCalledTime() : "+employeeDataAndStateDTO.getLastCalledTime());
			
			if(employeeDataAndStateDTO!=null && employeeDataAndStateDTO.getLastCalledTime()!= null && isAtleastOneMinutesAgo(employeeDataAndStateDTO.getLastCalledTime())) {
				System.out.println("Cron Job is going to get checked");
				autodialerReinitiateAndFunctionService.dialFromCron(campaign,employee,schedulerService);
			}
			else
			{
				System.out.println("Cron Job will not dial. Maybe its just dialed or busy");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private boolean isAtleastOneMinutesAgo(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		Instant oneMinutesAgo = Instant.now().minus(Duration.ofMinutes(1));

		try {
			return instant.isBefore(oneMinutesAgo);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
