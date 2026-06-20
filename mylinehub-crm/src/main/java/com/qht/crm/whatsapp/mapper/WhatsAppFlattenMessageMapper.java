package com.qht.crm.whatsapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

import com.qht.crm.whatsapp.dto.WhatsAppFlattenMessageDTO;
import com.qht.crm.whatsapp.entity.WhatsAppFlattenMessage;

@Mapper(componentModel = "spring",mappingControl = DeepClone.class)
public interface WhatsAppFlattenMessageMapper {
	WhatsAppFlattenMessageDTO mapWhatsAppFlattenMessageToDTO(WhatsAppFlattenMessage whatsAppFlattenMessage);
	WhatsAppFlattenMessage mapDTOToWhatsAppFlattenMessage(WhatsAppFlattenMessageDTO whatsAppFlattenMessageDTO);
	WhatsAppFlattenMessageDTO cloneWhatsAppFlattenMessage(WhatsAppFlattenMessageDTO whatsAppFlattenMessageDTO);
}