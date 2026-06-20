package com.qht.crm.whatsapp.dto.chat;

import com.qht.crm.whatsapp.entity.WhatsAppFlattenMessage;
import com.qht.crm.whatsapp.repository.WhatsAppFlattenMessageRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhatsAppFlattenMessageParameterDTO {
	String action;
	WhatsAppFlattenMessage whatsAppFlattenMessage;
	WhatsAppFlattenMessageRepository whatsAppFlattenMessageRepository;
}
