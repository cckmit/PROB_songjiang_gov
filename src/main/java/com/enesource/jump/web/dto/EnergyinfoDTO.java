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
 * 企业档案--用能信息
 * @author EDZ
 *
 */
@Data
public class EnergyinfoDTO extends ExcelBaseDTO {
	
	@Excel(name = "公司代码")
	private String companyId;
	
	/**
	 * 公司名称
	 */
	@Excel(name = "公司名称*")
	@NotBlank(message = "[公司名称]不能为空")
	private String entName; 
	
	
	/**
	 * 户号
	 */
	@Excel(name = "户号*")
	@NotBlank(message = "[户号]不能为空")
	private String accnumber; 
	
	
	/**
	 * 组织机构代码
	 */
	private String orgCode; 
	/**
	 * 能源类型，1 天然气，2 供热，3 用水，4 柴油零售， 5 柴油批发，6 汽油零售，7 汽油批发，8 煤，10 液化气
	 */
	private String energyType; 	
	/**
	 * 值
	 */
	private String value; 
	/**
	 * 时间
	 */
	private String date; 		
	/**
	 * 标煤转换数
	 */
	private String tce; 
	
	@ExcelEntity(name = "时间维度数据")
	private DateColumnDTO dateColumn;
	
//	@ExcelCollection(name = "时间维度数据", orderNum = "72")
//	private List<DateColumnDTO> dateColumn;

}
