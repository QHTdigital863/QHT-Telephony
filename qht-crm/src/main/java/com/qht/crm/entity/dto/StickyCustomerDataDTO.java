package com.qht.crm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StickyCustomerDataDTO {

     Long id;
     Long employeeId;
     Long customerId;
}
