package com.ssy.service;
import com.ssy.Entity.User;
import com.ssy.Mapper.UserMapper;
import com.ssy.util.PasswordEncryptionUtil;
import com.ssy.util.RedisService;
import com.ssy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisService redisService;
    public int Create(User user){
        if(userMapper.hasuser(user.getUsername())==1){
            return 0;
        }
        String secPassword= PasswordEncryptionUtil.encryptPassword(user.getPassword());
        user.setPassword(secPassword);
        return userMapper.Create(user);
    }

    public int Login(User user, HttpServletRequest httpRequest, HttpServletResponse response){
        String hashpassword=userMapper.selectPasswordByUsername(user.getUsername());
        if(PasswordEncryptionUtil.checkPassword(user.getPassword(),hashpassword)){
            User userinfo=userMapper.selectByName(user.getUsername());
            List<String> lokens=Arrays.asList(userinfo.getLikes().split(","));
            redisService.deleteKey(userinfo.getId()+"likes");
            for(int i=0;i< lokens.size();++i){
                redisService.insertElementAtHead(userinfo.getId()+"likes", lokens.get(i));
            }
            httpRequest.getSession(true);//会话开启
            String token=tokenUtil.createToken(user.getUsername());
            Cookie authCookie = new Cookie("AuthToken", token);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(true); // 在HTTPS环境下使用
            authCookie.setMaxAge(3600); // 设置过期时间
            authCookie.setPath("/");
            response.addCookie(authCookie);
            return userinfo.getId();
        }
        return 0;
    }
}
