package com.enesource.jump.web.dto;

import com.enesource.jump.web.common.Page;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

@Data
public class ExcelBaseDTO extends Page implements IExcelDataModel, IExcelModel {
	
	/**
	 * 行号
	 */
	private int rowNum;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
}
