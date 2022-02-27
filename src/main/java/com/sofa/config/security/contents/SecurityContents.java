package com.sofa.config.security.contents;

/**
 * 白名单
 */
public class SecurityContents {
    public static final String[] WHITE_LIST={
            "/user/login",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/configuration/security",
            "/doc.html",
            "/favicon.ico",
            "/tool/forget/password",
            "/tool/sms",
            "/user/sms/login",
            "/mini/login"
    };
}
