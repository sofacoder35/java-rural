package com.sofa.config.security.service;


import com.sofa.entity.SysMenu;
import com.sofa.entity.SysRole;
import com.sofa.entity.SysUser;
import com.sofa.mapper.SysUserMapper;
import com.sofa.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails

    loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user;
        if(redisUtil.hasKey("userInfo_"+username)){
            //缓存中存在用户信息，直接从缓存中取
           user= (SysUser) redisUtil.getValue("userInfo_"+username);
           redisUtil.expire("userInfo_"+username,5);
        }else {
            user=userMapper.findByUsername(username);
            if(user==null && username.length()==28){
                userMapper.insertOpenid(username);
                user=userMapper.findByUsername(username);

            }else if(user==null){
                throw new UsernameNotFoundException("用户名或密码错误");
            }

            if(user.getAdmin()){
                user.setRoles(userMapper.findRoles(null));
                user.setPermissions(userMapper.findPermissions(null));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(null);
                //获取子菜单
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenu(item.getId(), null)));
                user.setMenus(menus);
            } else {
                //非管理员需要查询
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(user.getId());
                //获取子菜单
                SysUser finalUser = user;
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenu(item.getId(), finalUser.getId())));
                user.setMenus(menus);
            }
            redisUtil.setValueTime("userInfo_"+username,user,5);

        }
        return user;
    }
}
