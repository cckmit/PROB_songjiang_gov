package com.enesource.jump.web.dto;

import com.enesource.jump.web.common.Page;

import lombok.Data;

@Data
public class AccnumberMdmidRelDTO extends Page {

	private String companyId;
	
	private String accnumber;
	
	private String type;
	
	private String deviceId;
	
	private String siteId;
	
	private Integer synele;
	
	private Double unitEleDay;
	
	private Double multiratio;
	
	private Double addratio;
	
	// 电表号
	private String[] mdmidArr;
}
