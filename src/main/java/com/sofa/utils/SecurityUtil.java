package com.sofa.utils;

import com.sofa.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;

public class SecurityUtil {
    /**
     * security中获取用户信息
     * @return
     */
    public static SysUser getUser(){
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        user.setName(user.getUsername());
        return user;
    }

    public static String getUsername(){
      return getUser().getUsername();
    }
    public static Long getUserId(){
        return getUser().getId();
    }

}
