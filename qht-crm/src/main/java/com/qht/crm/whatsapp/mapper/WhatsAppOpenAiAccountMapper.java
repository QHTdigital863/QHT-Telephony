package com.qht.crm.whatsapp.mapper;

import org.mapstruct.Mapper;

import com.qht.crm.whatsapp.dto.WhatsAppOpenAiAccountDto;
import com.qht.crm.whatsapp.entity.WhatsAppOpenAiAccount;


@Mapper(componentModel = "spring")
public interface WhatsAppOpenAiAccountMapper {
	
	WhatsAppOpenAiAccountDto mapWhatsAppOpenAiAccountToDTO(WhatsAppOpenAiAccount whatsAppOpenAiAccount);

	WhatsAppOpenAiAccount mapDTOToWhatsAppOpenAiAccount(WhatsAppOpenAiAccountDto whatsAppOpenAiAccountDto);
}