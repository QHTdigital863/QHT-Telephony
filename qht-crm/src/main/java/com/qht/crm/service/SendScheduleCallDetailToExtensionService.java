package com.qht.crm.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qht.crm.data.dto.CallToExtensionDTO;
import com.qht.crm.entity.Employee;
import com.qht.crm.entity.dto.BotInputDTO;
import com.qht.crm.ws.client.MyStompSessionHandler;

import lombok.AllArgsConstructor;

/**
 * @author Admin
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SendScheduleCallDetailToExtensionService {

	
public void  sendMessageToExtension(CallToExtensionDTO callToExtensionDTO, Employee employee) {	
		
		System.out.println("SendCurrentCallDetailToExtensionService");
		
		ObjectMapper mapper = new ObjectMapper();
		BotInputDTO msg = new BotInputDTO();
		
		msg = new BotInputDTO();
	  	msg.setDomain(employee.getDomain());
	  	msg.setExtension(employee.getExtension());
	  	msg.setFormat("json");
	  	try {
			msg.setMessage(mapper.writeValueAsString(callToExtensionDTO));
		} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	  	msg.setMessagetype("previewScheduleCall");
	  	msg.setOrganization(employee.getOrganization());
 	    try {
      	  MyStompSessionHandler.sendMessage("/qht/sendcalldetails", msg);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	}
}
