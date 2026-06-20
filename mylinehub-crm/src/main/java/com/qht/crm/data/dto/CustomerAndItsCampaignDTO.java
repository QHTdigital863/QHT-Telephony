package com.qht.crm.data.dto;

import java.util.Date;
import java.util.List;

import com.qht.crm.entity.Customers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAndItsCampaignDTO {

	List<Long> campaignIds;
	List<Customers> customers;
	boolean isCalledOnce;
	Date deletedDate;
	Date assignedDate;
	boolean triggerCustomerToExtentionInNewLineConnected = false;
	Long lastRunningCampaignID;
}
