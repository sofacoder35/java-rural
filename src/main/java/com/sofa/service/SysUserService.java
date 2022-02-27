package com.sofa.service;

import com.sofa.entity.SysUser;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import com.sofa.vo.LoginVo;
import com.sofa.vo.WxLoginVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sofa
 * @since 2022-01-19
 */
public interface SysUserService {
    /**
     * 登入接口
     * @param loginVo 账号密码
     * @return token
     */
    Result login(LoginVo loginVo);

    Result miniLogin(String openid);

    SysUser findByUsername(String username);

    Result findPage(QueryInfo queryInfo);

    Result insert(SysUser user);

    Result update(SysUser user);

    Result delete(Long id);

    void updatePwdByMail(String email, String password);

    Result updateByopenid(WxLoginVo wxLoginVo);
}
