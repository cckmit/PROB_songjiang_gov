package com.enesource.jump.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.IExceptionMapper;
import com.enesource.jump.web.dto.ExceptionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/* *
 * @author liuyy
 * @Description: 全局异常拦截
 * @date :created in 10:28 下午 2020/2/18
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    IExceptionMapper exceptionMapper;

    /**
     * @Author:liuyy
     * @Description: 自定义异常返回
     * @Date :11:01 下午 2020/2/18
     */
    @ExceptionHandler(value = ComponentException.class)
    @ResponseBody
    public void componentExceptionHandler(ComponentException e, HttpServletResponse response) {
        try {
            Result resultData = new Result();
            resultData.setMsg(e.getErrMessage());
            resultData.setCode(e.getErrCode());
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().append(JSON.toJSONString(resultData));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e, HttpServletRequest request) {
        Result result = new Result();
        result.setCode("0");
        result.setMsg(e.getMessage());
        String requestURI = request.getRequestURI();
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setType(1);
        exceptionLog.setUrl(requestURI);
        exceptionLog.setName("操作异常");
        exceptionLog.setLevel(1);
        exceptionLog.setInformation(e.getMessage());
        exceptionLog.setOperator("");
        exceptionLog.setCreateTime(new Date());
        exceptionMapper.saveExceptionInformation(exceptionLog);
        return result;
    }



}
