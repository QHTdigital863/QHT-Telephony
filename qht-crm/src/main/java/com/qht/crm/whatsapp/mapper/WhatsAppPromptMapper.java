package com.qht.crm.whatsapp.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.qht.crm.whatsapp.dto.WhatsAppPromptDto;
import com.qht.crm.whatsapp.entity.WhatsAppPhoneNumber;
import com.qht.crm.whatsapp.entity.WhatsAppPrompt;


@Mapper(componentModel = "spring")
public abstract class WhatsAppPromptMapper {
	
	
	@Mapping(target = "whatsAppPhoneNumberId", source = "whatsAppPrompt.whatsAppPhoneNumber.id")
	public abstract WhatsAppPromptDto mapWhatsAppPromptToDTO(WhatsAppPrompt whatsAppPrompt);
	
	@Mapping(target = "whatsAppPhoneNumber", source = "whatsAppPromptDto.whatsAppPhoneNumberId", qualifiedByName = "mapWhatsAppPhone")
	public abstract WhatsAppPrompt mapDTOToWhatsAppPrompt(WhatsAppPromptDto whatsAppPromptDto, @Context WhatsAppPhoneNumber whatsAppPhoneNumber);
	
	@Named("mapWhatsAppPhone") 
    WhatsAppPhoneNumber mapWhatsAppPhone(Long whatsAppPhoneNumberId, @Context WhatsAppPhoneNumber whatsAppPhoneNumber) throws Exception{

		if(whatsAppPhoneNumber == null)
		{
			throw new Exception("Cannot find phone number associated to template");
		}
		
		return whatsAppPhoneNumber;
	}
	
}