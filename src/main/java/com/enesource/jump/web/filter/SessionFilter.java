package com.enesource.jump.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enesource.jump.web.dao.ICommonMapper;
import com.enesource.jump.web.interceptor.RequestWrapper;
import com.enesource.jump.web.utils.JwtUtil;

@Component
public class SessionFilter implements Filter {
	
	@Autowired
	ICommonMapper commonMapper;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setHeader("Access-Control-Allow-Origin", "https://jiashan_gov.enesource.com");  
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET");  
		httpResponse.setHeader("Access-Control-Max-Age", "3600");  
		// 验证 token 需要添加传递
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, token");  
//		httpResponse.setHeader("Access-Control-Allow-Credentials", "true"); 
        
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		
		String url = req.getRequestURI();
		String token = req.getHeader("token");
		String userId = "";
        if(token != null && !"".equals(token)) {
        	userId = JwtUtil.getUserId(token);
        	
        }
        
	    Map<String, Object> opMap = new HashMap<>();
	      
	    opMap.put("url", url);
	    opMap.put("userId", userId);
	      
	    commonMapper.insertUserOperation(opMap);
	    
	    ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        if(requestWrapper == null) {
        	chain.doFilter(request, response);
        } else {
        	chain.doFilter(requestWrapper, response);
        }
        
//        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
