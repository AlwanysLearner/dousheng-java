package com.ssy.service;
import com.ssy.Entity.User;
import com.ssy.Mapper.UserMapper;
import com.ssy.util.PasswordEncryptionUtil;
import com.ssy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenUtil tokenUtil;
    public int Create(User user){
        if(userMapper.hasuser(user.getUsername())==1){
            return 0;
        }
        String secPassword= PasswordEncryptionUtil.encryptPassword(user.getPassword());
        user.setPassword(secPassword);
        return userMapper.Create(user);
    }

    public String Search(User user, HttpServletRequest httpRequest){
        String hashpassword=userMapper.selectPasswordByUsername(user.getUsername());
        if(PasswordEncryptionUtil.checkPassword(user.getPassword(),hashpassword)){
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("userinfo",user.getUsername());
            return tokenUtil.createToken(user.getUsername());
        }
        return "用户名密码错误";
    }
}
