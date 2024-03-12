package com.ssy.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncryptionUtil {
    // 加密密码
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 验证密码
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
