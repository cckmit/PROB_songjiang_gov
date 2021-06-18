package com.enesource.jump.web.dto;

import lombok.Data;
@Data
public class InsightDTO extends ParamDTO{
	private String areaCode;
	private String type;
	private String industryType;
	private String entName;
	private Integer proStatus;
	private Integer growthRate;
	private String outputValue;
	private String areaLabel;
	private String year;
	
	
	private String ordreBy;
	private String ordreFlag;
}
