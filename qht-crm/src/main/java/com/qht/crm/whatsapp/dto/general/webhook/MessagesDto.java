package com.qht.crm.whatsapp.dto.general.webhook;

import java.util.List;

import com.qht.crm.whatsapp.dto.general.LocationDto;
import com.qht.crm.whatsapp.dto.general.MediaDto;
import com.qht.crm.whatsapp.dto.general.OrderDto;
import com.qht.crm.whatsapp.dto.general.ReactionDto;
import com.qht.crm.whatsapp.dto.general.TextDto;
import com.qht.crm.whatsapp.dto.general.contact.ContactDto;
import com.qht.crm.whatsapp.dto.general.contact.UrlDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesDto {

	String from;
	String id;
	String timestamp;
	String type;
	ContextDto context;
	IdentityDto identity;
	InteractiveDto interactive;
	TextDto text;
	List<ErrorDto> errors;
	SystemMessageDto system;
	ButtonDto button;
	ReferralDto referral;
	ReactionDto reaction;
	LocationDto location;
	OrderDto order;
	ContactDto contacts;
	MediaDto audio;
	MediaDto document;
	MediaDto image;
	MediaDto sticker;
	MediaDto video;
}
