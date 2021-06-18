package com.enesource.jump.web.common;

import java.util.ArrayList;
import java.util.List;

import com.enesource.jump.web.dto.EntInfoDTO;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

public class CheckEntInfoDTOExcelVerifyHandler implements IExcelVerifyHandler<EntInfoDTO> {

	@Override
	public ExcelVerifyHandlerResult verifyHandler(EntInfoDTO obj) {
		
		List<String> industrylist = new ArrayList<String>(14);
		industrylist.add("交通运输、仓储和邮政业");
		industrylist.add("住宿和餐饮业");
		industrylist.add("信息传输、软件和信息技术服务业");
		industrylist.add("公共管理、社会保障和社会组织");
		industrylist.add("农、林、牧、渔业");
		industrylist.add("制造业");
		industrylist.add("卫生和社会工作");
		industrylist.add("居民服务、修理和其他服务业");
		industrylist.add("建筑业");
		industrylist.add("房地产业");
		industrylist.add("批发和零售业");
		industrylist.add("教育");
		industrylist.add("文化、体育和娱乐业");
		industrylist.add("水利、环境和公共设施管理业");
		industrylist.add("电力、热力、燃气及水生产和供应业");
		industrylist.add("科学研究和技术服务业");
		industrylist.add("租赁和商务服务业");
		industrylist.add("采矿业");
		industrylist.add("金融业");
		
		if(obj.getIndustryName() != null && !industrylist.contains(obj.getIndustryName().trim())) {
			return new ExcelVerifyHandlerResult(false,"行业门类名称错误！");
		}
		
		List<String> areaCodeList = new ArrayList<String>(14);
		areaCodeList.add("三江街道");
		areaCodeList.add("东城街道");
		areaCodeList.add("乌牛街道");
		areaCodeList.add("云岭乡");
		areaCodeList.add("北城街道");
		areaCodeList.add("南城街道");
		areaCodeList.add("大若岩镇");
		areaCodeList.add("岩坦镇");
		areaCodeList.add("岩头镇");
		areaCodeList.add("建筑业");
		areaCodeList.add("巽宅镇");
		areaCodeList.add("枫林镇");
		areaCodeList.add("桥下镇");
		areaCodeList.add("桥头镇");
		areaCodeList.add("沙头镇");
		areaCodeList.add("溪下乡");
		areaCodeList.add("瓯北街道");
		areaCodeList.add("界坑乡");
		areaCodeList.add("碧莲镇");
		areaCodeList.add("茗岙乡");
		areaCodeList.add("金溪镇");
		areaCodeList.add("鹤盛镇");
		areaCodeList.add("黄田街道");
		
		if(obj.getAreaName() != null && !areaCodeList.contains(obj.getAreaName().trim())) {
			return new ExcelVerifyHandlerResult(false,"行政区域错误！");
		}
		
		
		
		return new  ExcelVerifyHandlerResult(true);
	}

}
