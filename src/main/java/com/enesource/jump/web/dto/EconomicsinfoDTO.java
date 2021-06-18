package com.enesource.jump.web.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;


/***
 * 企业档案--经济信息
 * @author EDZ
 *
 */
@Data
public class EconomicsinfoDTO extends ExcelBaseDTO {
	
	@Excel(name = "公司代码")
	private String companyId;
	/**
	 * 公司名称
	 */
	@Excel(name = "公司名称*")
	@NotBlank(message = "[公司名称]不能为空")
	private String entName; 
	
	/**
	 * 值
	 */
	private String value; 
	/**
	 * 时间
	 */
	private String date; 		
	
	@ExcelEntity(name = "时间维度数据")
	private DateColumnDTO dateColumn;

}
