package com.enesource.jump.web.interceptor;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.hutool.core.lang.Validator;
import com.enesource.jump.web.dto.ExcelDayImport;
import com.enesource.jump.web.utils.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CheckExcelDayImportDTOExcelVerifyHandler implements IExcelVerifyHandler<ExcelDayImport> {

	@Override
	public ExcelVerifyHandlerResult verifyHandler(ExcelDayImport obj) {

		if(StringUtil.isNotEmpty(obj.get_Data01()) && !Validator.isNumber(obj.get_Data01())) {
			return new ExcelVerifyHandlerResult(false,"1日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data02()) && !Validator.isNumber(obj.get_Data02())) {
			return new ExcelVerifyHandlerResult(false,"2日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data03()) && !Validator.isNumber(obj.get_Data03())) {
			return new ExcelVerifyHandlerResult(false,"3日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data04())&& !Validator.isNumber(obj.get_Data04())) {
			return new ExcelVerifyHandlerResult(false,"4日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data05()) && !Validator.isNumber(obj.get_Data05())) {
			return new ExcelVerifyHandlerResult(false,"5日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data06()) && !Validator.isNumber(obj.get_Data06())) {
			return new ExcelVerifyHandlerResult(false,"6日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data07()) && !Validator.isNumber(obj.get_Data07())) {
			return new ExcelVerifyHandlerResult(false,"7日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data08()) && !Validator.isNumber(obj.get_Data08())) {
			return new ExcelVerifyHandlerResult(false,"8日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data09()) && !Validator.isNumber(obj.get_Data09())) {
			return new ExcelVerifyHandlerResult(false,"9日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data10()) && !Validator.isNumber(obj.get_Data10())) {
			return new ExcelVerifyHandlerResult(false,"10日数据类型错误！");
		}
		
        if(StringUtil.isNotEmpty(obj.get_Data11()) && !Validator.isNumber(obj.get_Data11())) {
			return new ExcelVerifyHandlerResult(false,"11日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data12()) && !Validator.isNumber(obj.get_Data12())) {
			return new ExcelVerifyHandlerResult(false,"12日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data13()) && !Validator.isNumber(obj.get_Data13())) {
			return new ExcelVerifyHandlerResult(false,"13日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data14()) && !Validator.isNumber(obj.get_Data14())) {
			return new ExcelVerifyHandlerResult(false,"14日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data15()) && !Validator.isNumber(obj.get_Data15())) {
			return new ExcelVerifyHandlerResult(false,"15日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data16()) && !Validator.isNumber(obj.get_Data16())) {
			return new ExcelVerifyHandlerResult(false,"16日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data17()) && !Validator.isNumber(obj.get_Data17())) {
			return new ExcelVerifyHandlerResult(false,"17日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data18()) && !Validator.isNumber(obj.get_Data18())) {
			return new ExcelVerifyHandlerResult(false,"18日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data19()) && !Validator.isNumber(obj.get_Data19())) {
			return new ExcelVerifyHandlerResult(false,"19日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data20()) && !Validator.isNumber(obj.get_Data20())) {
			return new ExcelVerifyHandlerResult(false,"20日数据类型错误！");
		}
		
		if(StringUtil.isNotEmpty(obj.get_Data21()) && !Validator.isNumber(obj.get_Data21())) {
			return new ExcelVerifyHandlerResult(false,"21日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data22()) && !Validator.isNumber(obj.get_Data22())) {
			return new ExcelVerifyHandlerResult(false,"22日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data23()) && !Validator.isNumber(obj.get_Data23())) {
			return new ExcelVerifyHandlerResult(false,"23日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data24()) && !Validator.isNumber(obj.get_Data24())) {
			return new ExcelVerifyHandlerResult(false,"24日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data25()) && !Validator.isNumber(obj.get_Data25())) {
			return new ExcelVerifyHandlerResult(false,"25日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data26())&& !Validator.isNumber(obj.get_Data26())) {
			return new ExcelVerifyHandlerResult(false,"26日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data27()) && !Validator.isNumber(obj.get_Data27())) {
			return new ExcelVerifyHandlerResult(false,"27日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data28()) && !Validator.isNumber(obj.get_Data28())) {
			return new ExcelVerifyHandlerResult(false,"28日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data29()) && !Validator.isNumber(obj.get_Data29())) {
			return new ExcelVerifyHandlerResult(false,"29日数据类型错误！");
		}
		if(StringUtil.isNotEmpty(obj.get_Data30()) && !Validator.isNumber(obj.get_Data30())) {
			return new ExcelVerifyHandlerResult(false,"30日数据类型错误！");
		}
        if(StringUtil.isNotEmpty(obj.get_Data31()) && !Validator.isNumber(obj.get_Data31())) {
			return new ExcelVerifyHandlerResult(false,"31日数据类型错误！");
		}
		return new ExcelVerifyHandlerResult(true);
	}



}
