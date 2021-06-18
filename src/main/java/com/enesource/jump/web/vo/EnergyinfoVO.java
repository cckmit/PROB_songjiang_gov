package com.enesource.jump.web.vo;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.common.Page;

import lombok.Data;


@Data
public class EnergyinfoVO extends Page{
	
	private String companyId;
	
	private String entName; 
	
	private String accumber;
	
	private String areaCode; 
	
	private String areaName; 
	
	// 当月用电量
	private Double thisValue;
	
	// 环比
	private Double monthRate;
	
	// 同比
	private Double yearRate;
	
	// 户号下线路占比
	private List<Map<String, Object>> accumberMdmidRel;

}
