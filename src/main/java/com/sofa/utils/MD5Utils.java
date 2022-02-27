package com.sofa.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Utils {
    /**
     * md5加密
     * @param password
     * @return
     */
    public static String md5(String password){
        if(StringUtils.isNotEmpty(password)){
            byte[] bytes=null;
            try {
                bytes = MessageDigest.getInstance("md5").digest(password.getBytes());
            } catch (NoSuchAlgorithmException e) {
                log.error("没有MD5这个加密算法");
            }
            StringBuilder code = new StringBuilder(new BigInteger(1, bytes).toString(16));
            for (int i = 0; i < 32 - code.length(); i++) {
                code.insert(0, "0");
            }
            return code.toString();
        }else {
            return null;
        }
    }

}
