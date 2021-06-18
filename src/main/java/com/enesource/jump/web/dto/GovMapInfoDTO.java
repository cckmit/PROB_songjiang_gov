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
 * 政府大屏企业信息
 * @author EDZ
 *
 */
@Data
public class GovMapInfoDTO extends ExcelBaseDTO {
	
	@Excel(name = "项目编号*", orderNum="0")
	private String siteId; 
	
	/**
	 * 站点类型，，1 发电厂、2 分布式光伏、3 电网、4 供热网、5 供冷网、6 储能、7 充电站,、8 水电站、9 垃圾发电站、10 变电站
	 */
	@Excel(name = "项目类型", replace = {"分布式光伏_2", "充电站_7", "水电站_8", "垃圾发电站_9", "变电站_10"}, orderNum="0")
	private Integer siteType;
	
	@Excel(name = "项目名称*", orderNum="0")
	@NotBlank(message = "[项目名称]不能为空")
	private String siteName; 	
	
	@Excel(name = "注册地址", orderNum="0")
	private String address; 	
	
	@Excel(name = "投运时间", orderNum="0")
	private String commissionTime; 		
	
	@Excel(name = "容量", orderNum="0")
//	@Pattern(regexp = "^[-\\\\+]?\\\\d+(\\\\.\\\\d*)?|\\\\.\\\\d+$", message = "值必须为数字")
	private String capacity; 	

	@Excel(name = "电压等级", orderNum="0")
	private String voltlevel; 	
	
	@Excel(name = "经度坐标", orderNum="0")
	private String lng;

	@Excel(name = "纬度坐标", orderNum="0")
	private String lat;
	
	// 区域标签
	private String areaLabel;
	
	/**
	 * *********************************充电站自有属性**************************************
	 */
	
	private String chargeType; 
	private String chargeCountSum; 	
	private String changePowerSum;
	private String capacityProportion;
	
	/**
	 * *********************************分布式光伏自有属性**************************************
	 */
	private String consNo; 
	private String declaredEnt; 
	private String photoNumber; 	
	private String investor; 	
	private String ongridDate; 	
	
	@ExcelEntity(name = "时间维度数据")
	private DateColumnDTO dateColumn;
	
	private String key; 	
}
