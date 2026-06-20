package com.qht.crm.whatsapp.mapper;

import org.mapstruct.Mapper;

import com.qht.crm.whatsapp.dto.WhatsAppProjectDto;
import com.qht.crm.whatsapp.entity.WhatsAppProject;

@Mapper(componentModel = "spring")
public interface WhatsAppProjectMapper {
	
	WhatsAppProjectDto mapWhatsAppProjectToDTO(WhatsAppProject whatsAppProject);

	WhatsAppProject mapDTOToWhatsAppProject(WhatsAppProjectDto whatsAppProjectDto);
}