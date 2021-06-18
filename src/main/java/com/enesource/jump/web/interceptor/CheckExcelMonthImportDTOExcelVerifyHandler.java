package com.enesource.jump.web.interceptor;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.hutool.core.lang.Validator;
import com.enesource.jump.web.dto.ExcelDayImport;
import com.enesource.jump.web.dto.ExcelMonthImport;
import com.enesource.jump.web.utils.StringUtil;

public class CheckExcelMonthImportDTOExcelVerifyHandler implements IExcelVerifyHandler<ExcelMonthImport> {

	@Override
	public ExcelVerifyHandlerResult verifyHandler(ExcelMonthImport obj) {

		if(StringUtil.isNotEmpty(obj.get_Data01()) && !Validator.isNumber(obj.get_Data01())) {
			return new ExcelVerifyHandlerResult(false,"1月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data02()) && obj.get_Data02() != null && !Validator.isNumber(obj.get_Data02())) {
			return new ExcelVerifyHandlerResult(false,"2月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data03()) && !Validator.isNumber(obj.get_Data03())) {
			return new ExcelVerifyHandlerResult(false,"3月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data04()) && !Validator.isNumber(obj.get_Data04())) {
			return new ExcelVerifyHandlerResult(false,"4月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data05()) && !Validator.isNumber(obj.get_Data05())) {
			return new ExcelVerifyHandlerResult(false,"5月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data06())  && !Validator.isNumber(obj.get_Data06())) {
			return new ExcelVerifyHandlerResult(false,"6月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data07()) && !Validator.isNumber(obj.get_Data07())) {
			return new ExcelVerifyHandlerResult(false,"7月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data08()) && !Validator.isNumber(obj.get_Data08())) {
			return new ExcelVerifyHandlerResult(false,"8月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data09()) && !Validator.isNumber(obj.get_Data09())) {
			return new ExcelVerifyHandlerResult(false,"9月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data10()) && !Validator.isNumber(obj.get_Data10())) {
			return new ExcelVerifyHandlerResult(false,"10月数据类型错误！");
		}
		
        if(StringUtil.isNotEmpty(obj.get_Data11()) && !Validator.isNumber(obj.get_Data11())) {
			return new ExcelVerifyHandlerResult(false,"11月数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data12()) && !Validator.isNumber(obj.get_Data12())) {
			return new ExcelVerifyHandlerResult(false,"12月数据类型错误！");
		}
		return new ExcelVerifyHandlerResult(true);
	}



}
