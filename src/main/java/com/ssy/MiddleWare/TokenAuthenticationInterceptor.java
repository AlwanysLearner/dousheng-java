package com.ssy.MiddleWare;

import com.ssy.Entity.ValidationResult;
import com.ssy.Mapper.UserMapper;
import com.ssy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TokenAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 会话存在且用户已登录
            return true; // 继续处理请求
        }
        // 获取请求头中的Token
        String token = request.getHeader("Authorization");

        // 这里实现你的Token验证逻辑
        ValidationResult validationResult = tokenUtil.verifyToken(token);
        if(!validationResult.isValid()){
            return false;
        }
        HttpSession newsession=request.getSession(true);
        newsession.setAttribute("userid",userMapper.selectByName(validationResult.getMessage()).getId());
        return true; // 如果Token有效，继续处理请求
    }
}
