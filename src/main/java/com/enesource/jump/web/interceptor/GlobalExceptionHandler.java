package com.enesource.jump.web.interceptor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.utils.Conf;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
		Result result = new Result();
		
		result.setCode(Conf.ERROR);
		result.setMsg(e.getMessage());

		return result;
    }
	
}
