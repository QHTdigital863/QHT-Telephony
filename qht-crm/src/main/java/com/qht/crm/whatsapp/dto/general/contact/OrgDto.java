package com.qht.crm.whatsapp.dto.general.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qht.crm.whatsapp.dto.general.webhook.ButtonDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgDto {

	String company;
	String department;
	String title;
}
