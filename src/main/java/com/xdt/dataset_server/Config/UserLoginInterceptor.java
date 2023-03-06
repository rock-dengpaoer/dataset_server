package com.xdt.dataset_server.Config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.Claim;
import com.xdt.dataset_server.utils.JwtUtil;
import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author XDT
 * @ClassName UserLoginInterceptor
 * @Description: TODO
 * @Date 2023/2/27 21:18
 **/
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行了拦截器的preHandle方法");
        try {
            /*获取header里的token*/
            final String token = request.getHeader("authorization");
            if(ObjectUtil.isNull(token)){
                log.warn("notFindToken");
                response.getWriter().write(JSONUtil.toJsonStr(Result.error("123", "notFindToken")));
                return false;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if(userData == null){
                //token过期
                log.warn("tokenOverdue");
                response.getWriter().write(JSONUtil.toJsonStr(Result.error("123", "tokenOverdue")));
                return false;
            }
            log.info("认证成功！");
            //System.out.println(userData.get("uuid").asString());
            request.setAttribute("userUuid",userData.get("uuid").asString());
            request.setAttribute("userName",userData.get("userName").asString());
            //System.out.println(userData.get("userName").asString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("执行了拦截器的postHandle方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        System.out.println("执行了拦截器的afterCompletion方法");
    }
}
