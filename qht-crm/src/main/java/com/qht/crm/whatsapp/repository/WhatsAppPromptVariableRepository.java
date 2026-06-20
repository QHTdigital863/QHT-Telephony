package com.qht.crm.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qht.crm.whatsapp.entity.WhatsAppPrompt;
import com.qht.crm.whatsapp.entity.WhatsAppPromptVariables;

@Repository
public interface WhatsAppPromptVariableRepository extends JpaRepository<WhatsAppPromptVariables, Long> {
   
	List<WhatsAppPromptVariables> getAllByOrganization(String organization);
	List<WhatsAppPromptVariables> getAllByOrganizationAndWhatsAppPrompt(String organization,WhatsAppPrompt whatsAppPrompt);
	List<WhatsAppPromptVariables> getAllByOrganizationAndWhatsAppPromptAndActive(String organization,WhatsAppPrompt whatsAppPrompt,boolean active);
	
}