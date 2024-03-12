package com.ssy.Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secretKey;

    // Getter
    public String getSecretKey() {
        return secretKey;
    }
}

