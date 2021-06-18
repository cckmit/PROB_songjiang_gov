package com.enesource.jump.web.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enesource.jump.web.annotation.JwtToken;
import com.enesource.jump.web.common.BaseAction;
import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.ExceptionLog;
import com.enesource.jump.web.utils.Conf;

@Controller
@CrossOrigin
public class View3DAction extends BaseAction{
	@Autowired
	IGovMapper govMapper;
	
	@JwtToken
	@RequestMapping(value = "/gov/findView3DValueList", method = RequestMethod.POST)  
	@ResponseBody
    public Result findEntList(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		try {
			String[] checkParamsMap = { "userId", "areaLabel", "date" };
			String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
			if (null != errorString) {
				result.setCode(Conf.ERROR);
				result.setMsg(errorString);

				return result;
			}
			
			List<Map<String, Object>> valueList = govMapper.findValueList(paramMap);
			
	        result.setData(valueList);
		} catch (Exception e) {
		    logger.error("3D可视化查询错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/gov/findView3DValueList");
		    exceptionLog.setName("3D可视化查询错误");
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
	
	
	@JwtToken
	@RequestMapping(value = "/gov/saveView3DIndex", method = RequestMethod.POST)  
	@ResponseBody
    public Result saveView3DIndex(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		try {
			String[] checkParamsMap = { "userId", "areaLabel" };
			String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
			if (null != errorString) {
				result.setCode(Conf.ERROR);
				result.setMsg(errorString);

				return result;
			}
			
			if(paramMap.get("id") == null || "".equals(paramMap.get("id").toString())) {
				String id = govMapper.getViewIndexUUID();
						
				paramMap.put("id", id);
			}
			
			govMapper.saveView3DIndex(paramMap);
			
			result.setData(paramMap.get("id"));
			
		} catch (Exception e) {
		    logger.error("保存3d指标库错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/gov/saveView3DIndex");
		    exceptionLog.setName("保存3d指标库错误");
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
	
	@JwtToken
	@RequestMapping(value = "/gov/delView3DIndex", method = RequestMethod.POST)  
	@ResponseBody
    public Result delView3DIndex(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		try {
			String[] checkParamsMap = { "userId", "areaLabel", "id" };
			String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
			if (null != errorString) {
				result.setCode(Conf.ERROR);
				result.setMsg(errorString);

				return result;
			}
			
			govMapper.delView3DIndex(paramMap);
			
		} catch (Exception e) {
		    logger.error("删除3d指标库错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/gov/delView3DIndex");
		    exceptionLog.setName("删除3d指标库错误");
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
	
	
	@JwtToken
	@RequestMapping(value = "/gov/findView3DIndex", method = RequestMethod.POST)  
	@ResponseBody
    public Result findView3DIndex(@RequestBody Map<String, Object> paramMap) {
		Result result = new Result();
		
		try {
			String[] checkParamsMap = { "userId", "areaLabel" };
			String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
			if (null != errorString) {
				result.setCode(Conf.ERROR);
				result.setMsg(errorString);

				return result;
			}
			
			List<Map<String, Object>> valueList = govMapper.findView3DIndex(paramMap);
			
			result.setData(valueList);
		} catch (Exception e) {
		    logger.error("3d指标库查询错误", e);
		    ExceptionLog exceptionLog = new ExceptionLog();
		    exceptionLog.setType(1);
		    exceptionLog.setUrl("/gov/findView3DIndex");
		    exceptionLog.setName("3d指标库查询错误");
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
