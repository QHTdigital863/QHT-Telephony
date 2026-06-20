package com.qht.crm.ws.controller;


import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.qht.crm.entity.dto.BotConferenceDTO;
import com.qht.crm.entity.dto.BotInputDTO;
import com.qht.crm.whatsapp.entity.WhatsAppChatHistory;
import com.qht.crm.whatsapp.enums.SEND_MESSAGE_KEYS;
import com.qht.crm.whatsapp.enums.chat.CHAT_ORIGIN;
import com.qht.crm.whatsapp.service.WhatsAppIntegrationOutboundService;


@Controller
public class ChatController {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	WhatsAppIntegrationOutboundService whatsAppOutboundIntegrationService;

    @MessageMapping("/sendevent")
    public void send(@Payload BotInputDTO inputDTO) throws Exception {
//    	System.out.println("I am inside websocket sendevent controller");
//    	System.out.println(inputDTO.toString());
    	this.simpMessagingTemplate.convertAndSend("/event/"+inputDTO.getOrganization(), inputDTO);
    }
    
    @MessageMapping("/sendcalldetails")
    public void sendToExtension(@Payload BotInputDTO inputDTO) throws Exception {
//    	System.out.println("I am inside websocket sendevent controller");
//    	System.out.println(inputDTO.toString());
    	this.simpMessagingTemplate.convertAndSend("/event/"+inputDTO.getExtension(), inputDTO);
    }
    
    @MessageMapping("/sendConferenceMessage")
    public void sendToConference(@Payload BotConferenceDTO inputDTO) throws Exception {
//    	System.out.println("I am inside websocket sendevent controller");
//    	System.out.println(inputDTO.toString());
    	this.simpMessagingTemplate.convertAndSend("/event/"+inputDTO.getConferenceId(), inputDTO);
    }
    
    @MessageMapping("/sendToWhatsAppPhone")
    public void sendToWhatsAppPhone(@Payload WhatsAppChatHistory inputDTO) throws Exception {
    	whatsAppOutboundIntegrationService.sendOutboundWhatsAppChatHistory(inputDTO,null);
    }

}
