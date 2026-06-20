package com.qht.crm.whatsapp.requests;

import java.util.Date;

import com.qht.crm.entity.Customers;
import com.qht.crm.entity.Employee;
import com.qht.crm.entity.Organization;
import com.qht.crm.entity.Purchases;
import com.qht.crm.whatsapp.entity.WhatsAppPhoneNumberTemplates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhatsAppTemplateVariableRequest {
	
	WhatsAppPhoneNumberTemplates whatsAppPhoneNumberTemplate;
	Organization organization;
	Customers customer;
	//A template is associated with product. Product is fetchd from template ehnce beow is not required.
	//Product product;
	Purchases purchase;
	Employee employee;
	String code;
	String amount;
	String text;
	Date date;
	Date today_date;
	Date yesterday_date;
	String currency;
	String name;
	String email;
	String parentorg;
	String reason;
	String retailer_id;
	String sub_type;
	String index;
	
}
