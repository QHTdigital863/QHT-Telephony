package com.qht.crm.whatsapp.mapper;

import org.mapstruct.Mapper;

import com.qht.crm.whatsapp.dto.WhatsAppNumberReportDto;
import com.qht.crm.whatsapp.entity.WhatsAppNumberReport;


@Mapper(componentModel = "spring")
public abstract class WhatsAppNumberReportMapper {
	
	public abstract WhatsAppNumberReportDto mapWhatsAppNumberReportToDTO(WhatsAppNumberReport whatsAppNumberReport);
	public abstract WhatsAppNumberReport mapDTOToWhatsAppNumberReport(WhatsAppNumberReportDto whatsAppNumberReportDto);
}

