package com.qht.crm.whatsapp.dto.chat;

import java.util.List;

import com.qht.crm.entity.Customers;
import com.qht.crm.entity.Organization;
import com.qht.crm.rag.dto.AiPropertyInventoryVerificationOutputDto;
import com.qht.crm.rag.model.AssistantEntity;
import com.qht.crm.repository.CustomerRepository;
import com.qht.crm.service.CustomerPropertyInventoryService;
import com.qht.crm.service.CustomerService;
import com.qht.crm.whatsapp.entity.WhatsAppChatHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhatsAppCustomerParameterDataDto {
	
	String action;
	//For Creating new customer
	int deleteLastNDays;
	String firstName;
	String lastName;
	String email;
	String phoneNumber;
	String whatsAppRegisteredByPhoneNumberId;
	String whatsAppRegisteredByPhoneNumber;
    String whatsApp_wa_id;
    String whatsAppDisplayPhoneNumber;
    String whatsAppPhoneNumberId;
    String whatsAppProjectId;
    String businessPortfolio;
    boolean turnOnAutoReply;
    Organization organization;

    
    //For AI
	private WhatsAppChatHistory whatsAppChatHistory;
	String whatsAppBotThread;
	String languageThread;
	String summarizeThread;
	String script;
	String language;
	boolean isBlockedForAI;
	AssistantEntity assistantEntity;
	
	String englishMessageToAdd;
	
	private AiPropertyInventoryVerificationOutputDto aiPropertyInventoryVerificationOutputDto;
	private CustomerPropertyInventoryService customerPropertyInventoryService;
	private List<String> cacheKeys; // keys like phone+org (your current format)

}
