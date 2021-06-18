package com.enesource.jump.web.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enesource.jump.web.utils.StringUtil;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.enesource.jump.web.annotation.JwtToken;
import com.enesource.jump.web.dao.ICommonMapper;
import com.enesource.jump.web.redis.IRedisService;
import com.enesource.jump.web.utils.Conf;
import com.enesource.jump.web.utils.JwtUtil;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IRedisService redisService;

    @Autowired
    ICommonMapper commonMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String ip = commonMapper.selectLimit1IP();

        if (StringUtil.isNotEmpty(ip)){
            String ipAddress = IPUtils.getRealIP(request);
            //所用需要验证的ip,暂时批量验证
            Integer i = commonMapper.checkwhitelist(ipAddress);
            if (i == null || i == 0) {
                response.setStatus(401);
                throw new RuntimeException("请检查ip白名单配置是否正确");
            }
        }
        
        String referer = request.getHeader("referer");
        if (referer == null || referer.indexOf("enesource") == -1) {
        	response.setStatus(401);
            throw new RuntimeException("请重新登陆");
		}
        
        

        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        
        String localUserId = "";
        
        if(JSONUtil.isJson(body)) {
        	JSONObject jsonObject = JSONUtil.parseObj(body);
        	
        	localUserId = StringUtil.getString(jsonObject.get("userId"));
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // TODO 生产环境需要放开
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(JwtToken.class)) {
            JwtToken jwtToken = method.getAnnotation(JwtToken.class);
            if (jwtToken.required()) {
                // 执行认证
                if (token == null) {
                    response.setStatus(401);
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 userId
                String userId = JwtUtil.getUserId(token);

                // 验证 token
                String redisToken = redisService.get(Conf.LOGINTOKEN + localUserId);

                if (redisToken == null) {
                    response.setStatus(401);

                    throw new RuntimeException("token 无效，请重新登录");
                }

                if (!redisToken.equals(token)) {
                    response.setStatus(401);

                    throw new RuntimeException("token 无效，请重新登录");
                }

                redisService.expire(Conf.LOGINTOKEN + userId, 1200);
                response.addHeader("Set-Cookie", "HTTPOnly;");

            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
