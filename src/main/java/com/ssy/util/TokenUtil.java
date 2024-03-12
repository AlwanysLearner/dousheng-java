package com.ssy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssy.Entity.JwtProperties;
import com.ssy.Entity.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class TokenUtil {

    private final JwtProperties jwtProperties;
    @Autowired
    public TokenUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public  String createToken(String username){
        String secretKey = jwtProperties.getSecretKey();
        String token = JWT.create()
                .withIssuer("dousheng") // 发行者
                .withSubject(username) // 主题
                .withIssuedAt(new Date()) // 签发时间
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 过期时间，这里设置为1小时后
                .sign(Algorithm.HMAC256(secretKey)); // 使用HMAC256算法进行签名

        return token;
    }

    public ValidationResult verifyToken(String token) {
        String secret = jwtProperties.getSecretKey();
        try {
            // 使用与生成令牌时相同的secret和算法创建一个验证器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("dousheng")
                    .build(); // 使用与创建令牌时相同的发行者配置验证器

            // 验证令牌
            DecodedJWT jwt = verifier.verify(token);


            return new ValidationResult(true, jwt.getSubject());

        } catch (JWTVerificationException exception) {
            // 令牌无效或签名不匹配
            return new ValidationResult(false,"Token verification failed: " + exception.getMessage());
        }
    }
}
