package com.enesource.jump.web.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ImportDayValueDTO extends ExcelBaseDTO {

	@Excel(name = "企业Id")
	@NotBlank(message = "[企业Id]不能为空")
	private String companyId; 
	
	@Excel(name = "企业名称")
	@NotBlank(message = "[企业名称]不能为空")
	private String entName;
	
	@Excel(name = "户号")
	@NotBlank(message = "[户号]不能为空")
	private String accnumber; 	
	
	@Excel(name = "用电量")
	private String value;
	
	
	private String date;
}
