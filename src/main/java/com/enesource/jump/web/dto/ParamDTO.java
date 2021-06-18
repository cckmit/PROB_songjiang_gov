package com.enesource.jump.web.dto;

import com.enesource.jump.web.common.Page;

import lombok.Data;

@Data
public class ParamDTO extends Page{
	private String userId;
	private String key;
	private String type;
	private String orgCode;
	private String companyId;
	private String areaLabel;
	private String date;
	private String microPark;
	private String areaCode;

	private String monday;
	private String productionStatus;
	private String industry;
	/**
	 * 选择的日期类型
	 */
	private String selectType;
	/**
	 *
	 */
	private String dataType;

}
