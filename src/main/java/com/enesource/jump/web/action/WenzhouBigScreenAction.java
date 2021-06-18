package com.enesource.jump.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enesource.jump.web.annotation.JwtToken;
import com.enesource.jump.web.common.BaseAction;
import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.ExceptionLog;
import com.enesource.jump.web.dto.ParamDTO;
import com.enesource.jump.web.utils.Conf;
import com.enesource.jump.web.utils.MapUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 温州原 tableau大屏
 * @author EDZ
 *
 */
@Controller
@CrossOrigin
public class WenzhouBigScreenAction extends BaseAction {

	@Autowired
	IGovMapper govMapper;
	
	
	/**
	 * 各产能状态企业数量
	 * @param paramMap
	 * @return
	 */
	@JwtToken
	@RequestMapping("/show/findStateTrend")
	@ResponseBody
	public Result findStateTrend(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		String[] checkParamsMap = { "userId" };
		String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
		if (null != errorString) {
			result.setCode(Conf.ERROR);
			result.setMsg(errorString);

			return result;
		}
		
		try {
			List<Map<String, Object>> rm = govMapper.findStateTrend(paramMap);
			
			result.setData(rm);
			
		} catch (Exception e) {
		    logger.error("各产能状态企业数量错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/findStateTrend");
		    exceptionLog.setName("各产能状态企业数量错误");
		    exceptionLog.setLevel(1);
		    exceptionLog.setInformation(e.getMessage());
		    exceptionLog.setOperator("");

		    this.saveExceptionLog(exceptionLog);
		    
		    result.setCode(Conf.ERROR);
		    result.setMsg("执行异常");
		    
		    return result;
		}

		return result;
	}
	
	
	/**
	 * 各产能状态企业详情
	 * @param paramMap
	 * @return
	 */
	@JwtToken
	@RequestMapping("/show/findStateTrendDetail")
	@ResponseBody
	public Result findStateTrendDetail(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		String[] checkParamsMap = { "userId", "monday", "productionStatus" };
		String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
		if (null != errorString) {
			result.setCode(Conf.ERROR);
			result.setMsg(errorString);

			return result;
		}
		
		try {
			Map<String, Object> rsMap = new HashMap<String, Object>();
			
			ParamDTO dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
			
			if (dto.getPage() != null) {
				PageHelper.startPage(dto.getPage(), 5);
			}
			
			List<Map<String, Object>> rm = govMapper.findStateTrendEnt(dto);
			
			List<Map<String, Object>> rateList = govMapper.findStateTrendEntRate(paramMap);
			
			rsMap.put("rateList", rateList);
			rsMap.put("list", new PageInfo(rm));
			
			result.setData(rsMap);
			
		} catch (Exception e) {
		    logger.error("各产能状态企业详情错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/findStateTrendDetail");
		    exceptionLog.setName("各产能状态企业详情错误");
		    exceptionLog.setLevel(1);
		    exceptionLog.setInformation(e.getMessage());
		    exceptionLog.setOperator("");

		    this.saveExceptionLog(exceptionLog);
		    
		    result.setCode(Conf.ERROR);
		    result.setMsg("执行异常");
		    
		    return result;
		}

		return result;
	}
	
	
	/**
	 * 增产企业数量变化趋势
	 * @param paramMap
	 * @return
	 */
	@JwtToken
	@RequestMapping("/show/findIncreaseProductionlEntNum")
	@ResponseBody
	public Result findIncreaseProductionlEntNum(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		String[] checkParamsMap = { "userId" };
		String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
		if (null != errorString) {
			result.setCode(Conf.ERROR);
			result.setMsg(errorString);

			return result;
		}
		
		try {
			List<Map<String, Object>> entNumList = govMapper.findIncreaseProductionlEntNum(paramMap);
			
			result.setData(entNumList);
			
		} catch (Exception e) {
		    logger.error("增产企业数量变化趋势错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/findIncreaseProductionlEntNum");
		    exceptionLog.setName("增产企业数量变化趋势错误");
		    exceptionLog.setLevel(1);
		    exceptionLog.setInformation(e.getMessage());
		    exceptionLog.setOperator("");

		    this.saveExceptionLog(exceptionLog);
		    
		    result.setCode(Conf.ERROR);
		    result.setMsg("执行异常");
		    
		    return result;
		}

		return result;
	}
	
	/**
	 * 五大行业周电量
	 * @param paramMap
	 * @return
	 */
	@JwtToken
	@RequestMapping("/show/findIndustryWeekEle")
	@ResponseBody
	public Result findIndustryWeekEle(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		String[] checkParamsMap = { "userId" };
		String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
		if (null != errorString) {
			result.setCode(Conf.ERROR);
			result.setMsg(errorString);

			return result;
		}
		
		try {
			
			List<Map<String, Object>> industryWeekEleList = govMapper.findIndustryWeekEle(paramMap);
			
			result.setData(industryWeekEleList);
			
		} catch (Exception e) {
		    logger.error("五大行业周电量错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/findIndustryWeekEle");
		    exceptionLog.setName("五大行业周电量错误");
		    exceptionLog.setLevel(1);
		    exceptionLog.setInformation(e.getMessage());
		    exceptionLog.setOperator("");

		    this.saveExceptionLog(exceptionLog);
		    
		    result.setCode(Conf.ERROR);
		    result.setMsg("执行异常");
		    
		    return result;
		}

		return result;
	}
	
	
	/**
	 * 行业基准电量弹窗
	 * @param paramMap
	 * @return
	 */
	@JwtToken
	@RequestMapping("/show/findIndustryUnitEle")
	@ResponseBody
	public Result findIndustryUnitEle(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		String[] checkParamsMap = { "userId" };
		String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
		if (null != errorString) {
			result.setCode(Conf.ERROR);
			result.setMsg(errorString);

			return result;
		}
		
		try {
			
			List<Map<String, Object>> IndustryUnitEleList = govMapper.findIndustryUnitEle(paramMap);
			
			result.setData(IndustryUnitEleList);
			
		} catch (Exception e) {
		    logger.error("行业基准电量错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/findIndustryUnitEle");
		    exceptionLog.setName("行业基准电量错误");
		    exceptionLog.setLevel(1);
		    exceptionLog.setInformation(e.getMessage());
		    exceptionLog.setOperator("");

		    this.saveExceptionLog(exceptionLog);
		    
		    result.setCode(Conf.ERROR);
		    result.setMsg("执行异常");
		    
		    return result;
		}

		return result;
	}
}
