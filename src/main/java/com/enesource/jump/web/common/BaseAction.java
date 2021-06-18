package com.enesource.jump.web.common;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.enesource.jump.web.dao.IExceptionMapper;
import com.enesource.jump.web.dto.ExceptionLog;
import com.enesource.jump.web.utils.Conf;

public class BaseAction extends Conf {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	IExceptionMapper exceptionMapper;

	//自定义时间格式
	public static String YMD_TIME_PATTERN = "yyyy-MM-dd";

	public static Map<String, String> ERROR_STRING_MAP   = new HashMap<String, String>();
	
	static {
		ERROR_STRING_MAP.put("userName", "用户名不得为空");
		ERROR_STRING_MAP.put("password", "密码不得为空");
		ERROR_STRING_MAP.put("timestamp", "时间戳不得为空");
		ERROR_STRING_MAP.put("companyId", "企业id不得为空");
		ERROR_STRING_MAP.put("type", "类型不得为空");
		ERROR_STRING_MAP.put("parentId", "上层id不得为空");
		ERROR_STRING_MAP.put("date", "时间不得为空");
		ERROR_STRING_MAP.put("mdmid", "表计编号不得为空");
		ERROR_STRING_MAP.put("flag", "flag标记不得为空");
		ERROR_STRING_MAP.put("firstDate", "起始时间不得为空");
		ERROR_STRING_MAP.put("operation", "操作标记不得为空");
		ERROR_STRING_MAP.put("companyIds", "企业id数组不得为空");
		ERROR_STRING_MAP.put("startDate", "起始时间不得为空");
		ERROR_STRING_MAP.put("endDate", "截至时间不得为空");
		ERROR_STRING_MAP.put("id", "id不得为空");
		ERROR_STRING_MAP.put("level", "等级不得为空");
		ERROR_STRING_MAP.put("menuUrl", "目录菜单不得为空");
		ERROR_STRING_MAP.put("subType", "子类型不得为空");
		ERROR_STRING_MAP.put("userId", "用户id不得为空");
		ERROR_STRING_MAP.put("deviceId", "设备编号不得为空");
		ERROR_STRING_MAP.put("readTime", "读取时间不得为空");
		ERROR_STRING_MAP.put("startTime", "起始时间不得为空");
		ERROR_STRING_MAP.put("endTime", "截至时间不得为空");
		ERROR_STRING_MAP.put("active", "事件状态不得为空");
		ERROR_STRING_MAP.put("opflag", "opflag标记不得为空");
		ERROR_STRING_MAP.put("readState", "状态不得为空");
		ERROR_STRING_MAP.put("point", "采集点不得为空");
		ERROR_STRING_MAP.put("client_id", "客户端ID不得为空");
		ERROR_STRING_MAP.put("client_secret", "客户端密码不得为空");
		ERROR_STRING_MAP.put("code", "客户端编号不得为空");
		ERROR_STRING_MAP.put("grant_type", "许可类型不得为空");
		ERROR_STRING_MAP.put("objectId", "objectId不得为空");
		ERROR_STRING_MAP.put("site_id", "site_id不得为空");
		ERROR_STRING_MAP.put("task_id", "task_id不得为空");
		ERROR_STRING_MAP.put("year", "年份不得为空");
		ERROR_STRING_MAP.put("phone", "电话号码不得为空");
		ERROR_STRING_MAP.put("verifyCode", "验证码不得为空");
		ERROR_STRING_MAP.put("companyName", "企业名称不得为空");
		ERROR_STRING_MAP.put("regisId", "工商注册号不得为空");
		ERROR_STRING_MAP.put("fileId", "上传文件不得为空");
		ERROR_STRING_MAP.put("authStatus", "认证状态不得为空");
		ERROR_STRING_MAP.put("value", "值不得为空");
		ERROR_STRING_MAP.put("time", "时间不得为空");
		ERROR_STRING_MAP.put("regisId", "工商注册号不得为空");
		ERROR_STRING_MAP.put("consNo", "电力户号不得为空");
		ERROR_STRING_MAP.put("projectType", "补贴项目类型不得为空");
		ERROR_STRING_MAP.put("projectId", "项目编号不得为空");
		ERROR_STRING_MAP.put("auditStatus", "审核状态不得为空");
		ERROR_STRING_MAP.put("nonce", "随机字符不得为空");
		ERROR_STRING_MAP.put("sign", "签名不得为空");
		ERROR_STRING_MAP.put("consLng", "经度不得为空");
		ERROR_STRING_MAP.put("consLat", "纬度不得为空");
		ERROR_STRING_MAP.put("regionId", "区域ID不得为空");
		ERROR_STRING_MAP.put("roadId", "街道ID不得为空"); 
		ERROR_STRING_MAP.put("openId", "微信openId不得为空"); 
		ERROR_STRING_MAP.put("loginName", "账号不能为空");
		ERROR_STRING_MAP.put("pwd", "密码不能为空");
		ERROR_STRING_MAP.put("corpId", "所在企业不能为空"); 
		ERROR_STRING_MAP.put("regionId", "区域编号不能为空");
		ERROR_STRING_MAP.put("siteid", "企业站点id不能为空");
		ERROR_STRING_MAP.put("operation", "operation不能为空");
		ERROR_STRING_MAP.put("qymc", "企业名称不能为空");
		ERROR_STRING_MAP.put("year", "year不能为空");
		ERROR_STRING_MAP.put("yuefen", "月份不能为空");
		ERROR_STRING_MAP.put("regions", "区域不能为空");
		ERROR_STRING_MAP.put("years", "years不能为空");
		ERROR_STRING_MAP.put("keepRecord", "项目备案号不能为空");
		ERROR_STRING_MAP.put("dateTime", "时间不能为空");
		ERROR_STRING_MAP.put("name", "名称不能为空");
		ERROR_STRING_MAP.put("exists", "是否关联标记不能为空");
		ERROR_STRING_MAP.put("page", "页码不能为空");
		ERROR_STRING_MAP.put("shortName", "姓名不能为空");
		ERROR_STRING_MAP.put("consArray", "户号列表不能为空");
		ERROR_STRING_MAP.put("imgVerifyCode", "图形验证码不能为空");
		ERROR_STRING_MAP.put("messageType", "系统异常,请核对信息");
		ERROR_STRING_MAP.put("siteName", "名称不能为空");
		ERROR_STRING_MAP.put("orgCode", "组织机构编码不能为空");
		ERROR_STRING_MAP.put("entName", "企业名称不能为空");
		ERROR_STRING_MAP.put("key", "查询关键字不能为空");
		ERROR_STRING_MAP.put("dateType", "时间选择类型不能为空");
		ERROR_STRING_MAP.put("selectType", "查询类型不能为空");
		ERROR_STRING_MAP.put("energyType", "能源类型不能为空");
		ERROR_STRING_MAP.put("siteType", "项目类型不能为空");
		ERROR_STRING_MAP.put("microPark", "园区名称不能为空");
		
		
		ERROR_STRING_MAP.put("societyConsumeAct", "参数不能为空");
		ERROR_STRING_MAP.put("societyConsumeTag", "参数不能为空");
		ERROR_STRING_MAP.put("societyPowerAct", "参数不能为空");
		ERROR_STRING_MAP.put("societyPowerTag", "参数不能为空");
		ERROR_STRING_MAP.put("societyWaterAct", "参数不能为空");
		ERROR_STRING_MAP.put("societyWaterTag", "参数不能为空");
		ERROR_STRING_MAP.put("societyCarbonAct", "参数不能为空");
		ERROR_STRING_MAP.put("societyCarbonTag", "参数不能为空");
		ERROR_STRING_MAP.put("gaeRateAct", "参数不能为空");
		ERROR_STRING_MAP.put("gaeRateTag", "参数不能为空");
		ERROR_STRING_MAP.put("cleanRateAct", "参数不能为空");
		ERROR_STRING_MAP.put("cleanRateTag", "参数不能为空");
		ERROR_STRING_MAP.put("photovoSum", "参数不能为空");
		ERROR_STRING_MAP.put("batterySum", "参数不能为空");
		ERROR_STRING_MAP.put("chargeSum", "参数不能为空");
		ERROR_STRING_MAP.put("waterPowerSum", "参数不能为空");
		ERROR_STRING_MAP.put("garbagePowerSum", "参数不能为空");
		ERROR_STRING_MAP.put("substationSum", "参数不能为空");
		ERROR_STRING_MAP.put("entSum", "参数不能为空");
		ERROR_STRING_MAP.put("societyIndustryAct", "参数不能为空");
		ERROR_STRING_MAP.put("societyIndustryTag", "参数不能为空");
		ERROR_STRING_MAP.put("fuheAvg", "平均负荷不能为空");
		ERROR_STRING_MAP.put("fuheMax", "最大负荷不能为空");
		ERROR_STRING_MAP.put("areaContract", "区域容量不能为空");
		ERROR_STRING_MAP.put("capacityProportion", "容载比不能为空");

		
		ERROR_STRING_MAP.put("areaLabel", "区域标签不能为空");
		ERROR_STRING_MAP.put("accnumber", "电力户号不得为空");
		
		ERROR_STRING_MAP.put("webUUID", "页面请求标记不得为空");
		
		ERROR_STRING_MAP.put("monday", "周标记不得为空");
		ERROR_STRING_MAP.put("productionStatus", "产能状态不得为空");
		
	}
	
	
	public void saveExceptionLog(ExceptionLog exceptionLog) {
		exceptionMapper.saveExceptionInformation(exceptionLog);
    }
	
	/**
	 * 记录用户错误信息
	 * @author liuyy
	 * @date 2019年6月26日 下午8:08:17
	 */
	public void saveExceptionLogs(String type,String url,String errMSg,String level,String operator,Exception e) {
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setType(1);
	    exceptionLog.setUrl("/user/login");
	    exceptionLog.setName("用户登录错误");
	    exceptionLog.setLevel(1);
	    exceptionLog.setInformation(e.getMessage());
	    exceptionLog.setOperator("");
	    this.saveExceptionLog(exceptionLog);
	}
	
	
	/**
	 * 参数错误校验
	 * @param requestParamsMap
	 * @param checkParamsMap
	 * @param errorString
	 * @return
	 */
	public String checkParams(Map<String, Object> requestParamsMap, String[] checkParamsMap, Map<String, String> errorString) {
        for (String checkP : checkParamsMap) {
            if (null == requestParamsMap.get(checkP)) {
                return errorString.get(checkP);
            }
            
            if ("".equals(requestParamsMap.get(checkP).toString().trim())) {
                return errorString.get(checkP);
            }
        }
        return null;
    }
}
