package com.qht.crm.rag.service;


import com.qht.crm.rag.model.AIError;
import com.qht.crm.rag.repository.AIErrorResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIErrorLoggingService { 
	
	@Autowired private AIErrorResponseRepository repo;
	
	public void logError(String organization, String sessionId, String assistantId, String errorString, String responseString) 
	{ 
		AIError err = new AIError();
		err.setOrganization(organization);
		err.setSessionId(sessionId);
		err.setAssistantId(assistantId);
		err.setErrorString(errorString);
		err.setResponseString(responseString); 
		repo.save(err); 
		System.err.println("Logged AI Error for session " + sessionId); 
	}
}