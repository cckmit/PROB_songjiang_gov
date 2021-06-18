package com.enesource.jump.web.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;


/***
 * 企业档案信息
 * @author EDZ
 *
 */
@Data
public class EntInfoDTO extends ExcelBaseDTO {
	
	/**
	 * ***************************************************************企业基础信息***********************************************************************************
	 */
	
	@Excel(name = "公司代码")
	private String companyId;
	
	@Excel(name = "公司名称*")
	@NotBlank(message = "[公司名称]不能为空")
	private String entName; 	
	
//	@Excel(name = "企业别名")
//	private String entNameOther; 
	
	@Excel(name = "组织机构代码")
	private String orgCode; 
	
	@Excel(name = "统一社会信用代码")
	private String creditCode; 	

//	@Excel(name = "法人代表")
//	private String legalPerson; 

	@Excel(name = "成立日期")
	private String registerDate; 		
	
//	@Excel(name = "行业门类")
	private String industry; 		

//	@Excel(name = "行业门类名称")
	private String industryName; 
	
//	@Excel(name = "行业大类")
	private String industryType; 		

	@Excel(name = "行业大类名称")
	private String industryTypeName; 
	
	@Excel(name = "注册资金")
	private Double fund;
	
	/**
	 * ***************************************************************企业位置信息***********************************************************************************
	 */

	@Excel(name = "详细地址")
	private String address; 
	
	@Excel(name = "经度坐标")
	private String lng;
	
	@Excel(name = "纬度坐标")
	private String lat;

	@Excel(name = "所属小微园区")
	private String microPark;

//	@Excel(name = "行政区划编号")
	private String areaCode; 	

	@Excel(name = "行政区域")
	private String areaName; 
	
	private String province;
	private String provinceName;
	private String city;
	private String cityName;
	private String region;
	private String regionName;
	
	
	/**
	 * ***************************************************************企业经营信息***********************************************************************************
	 */

	@Excel(name = "占用土地（亩）")
	private String coveredArea; 
	
	@Excel(name = "建筑面积")
	private String builtArea; 
	
	@Excel(name = "用工规模（人）")
//	@Pattern(regexp = "/^[1-9]+\\\\d*$", message = "值必须为整数")
	private String peopleNum; 	
	
	@Excel(name = "产值规模")
	private String outputValue;
	
	@Excel(name = "主要业务活动")
	private String business;


	// 企业联络人
	private String contactPerson;
	private String contactPhone;
	private Integer isOpenMssage;
	
	
	/**
	 * ***************************************************************标签信息***********************************************************************************
	 */
	
	// 标签
	private String[] tagArr;
	
	
	/**
	 * ***************************************************************资料文件信息***********************************************************************************
	 */
	
	// 文件
	private List<FileDTO> fileArr;
	
	
	
	/**
	 * ***************************************************************电力户号信息***********************************************************************************
	 */
	
	// 电表号
	private String[] mdmidArr;
	
//	@Excel(name = "工商注册号")
//	private String registrationNumber; 
	
	// 区域标签
	private String areaLabel;
	
	
	// 页面查询条件
	private String key;
	private String year;
	
	// 电力户号
	private String[] powerNoList;
	
	// 天然气户号
	private String[] gasNoList;
	
	// 热户号
	private String[] hotNoList;
	
	// 水户号
	private String[] waterNoList;
	
	// 柴油户号
	private String[] oilNoList;
	
	// 汽油户号
	private String[] petrolNoList;
	
	// 煤户号
	private String[] coalNoList;
		
		
	
}
