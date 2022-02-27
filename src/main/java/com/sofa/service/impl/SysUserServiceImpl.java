package com.sofa.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sofa.config.security.service.UserDetailsServiceImpl;
import com.sofa.entity.SysRole;
import com.sofa.entity.SysUser;
import com.sofa.mapper.SysUserMapper;
import com.sofa.service.SysUserService;
import com.sofa.utils.*;
import com.sofa.vo.LoginVo;
import com.sofa.vo.WxLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result login(LoginVo loginVo) {
        UserDetails userDetails;
        //验证码
        if(loginVo.getType()==2){
            if(!StringUtils.isNotEmpty(loginVo.getPhoneNumber()) || !StringUtils.isNotEmpty(loginVo.getCode())){
                return Result.fail("请输入完整");
            }
            Object code = redisUtil.getValue(loginVo.getPhoneNumber() + "sms");
            if(code==null){
               return Result.fail("验证码过期");
            }
            if(!code.toString().equals(loginVo.getCode())){
               return Result.fail("验证码不正确");
            }
            userDetails = userDetailsService.loadUserByUsername(loginVo.getPhoneNumber());
        }else{
            //账号密码
            if(!StringUtils.isNotEmpty(loginVo.getUsername()) || !StringUtils.isNotEmpty(loginVo.getPassword())){
                return Result.fail("请输入完整");
            }
            log.info("1开始登入");
            userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
            log.info("2判断用户是否存在密码是否正确");
            if(userDetails==null || !passwordEncoder.matches(MD5Utils.md5(loginVo.getPassword()),userDetails.getPassword())){
                return Result.fail("账号或密码错误");
            }

        }
        if(!userDetails.isEnabled()){
            return Result.fail("该账号已禁用");
        }
        log.info("登入成功，在security对象中存入登入信息");
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登入信息获取token");
        Map<String ,String>map=new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        String token=tokenUtil.generateToken(userDetails);
        map.put("token",token);
        return Result.success("登入成功",map);
    }

    @Override
    public Result miniLogin(String openid) {
        UserDetails userDetails;
        userDetails=userDetailsService.loadUserByUsername(openid);
//        if(userDetails==null){
//            userDetails=userDetailsService.loadUserByUsername(openid);
//        }
        if(!userDetails.isEnabled()){
            return Result.fail("该账号已禁用");
        }
        log.info("微信小程序登入成功，在security对象中存入登入信息");
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登入信息获取token");
        Map<String ,Object>map=new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        String token=tokenUtil.generateToken(userDetails);
        map.put("token",token);
        map.put("userInfo",userDetails);
        map.put("openid",openid);
        return Result.success("登入成功",map);
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("分页查询--> 页码==>{} 页数大小==>{}",queryInfo.getPageNumber(),queryInfo.getPageSize());
        PageHelper .startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysUser> page = sysUserMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysUser> result = page.getResult();
        result.forEach(item->{
            item.setRoles(sysUserMapper.findRoles(item.getId()));
            item.setName(item.getUsername());
        });
        return PageResult.pageResult(total,result);
    }

    @Transactional
    @Override
    public Result insert(SysUser user) {
        log.info("根据用户名查询用户信息");
        SysUser sysUser = sysUserMapper.findByUsername(user.getUsername());
        if (sysUser!=null) {
            return Result.fail("用户名已存在");
        }
        log.info("加密");
        user.setPassword(passwordEncoder.encode(MD5Utils.md5(user.getPassword())));
        log.info("1,添加用户信息");
        sysUserMapper.insert(user);
        log.info("2,添加角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size()>0) {
            roles.forEach(item->{
                sysUserMapper.insertUserRole(user.getId(), item.getId());
            });
        }
        log.info("3,添加用户角色有{}个",roles.size());
        return Result.success("用户添加成功");
    }

    @Transactional
    @Override
    public Result update(SysUser user) {
        log.info("1,先将角色用户信息删除");
        sysUserMapper.deleteRolesByUserId(user.getId());
        log.info("2,添加角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size()>0) {
            roles.forEach(item->{
                sysUserMapper.insertUserRole(user.getId(), item.getId());
            });
        }
        user.setPassword(passwordEncoder.encode(MD5Utils.md5(user.getPassword())));
        log.info("3,修改用户信息");
        sysUserMapper.update(user);
        redisUtil.delKey("userInfo_"+ SecurityUtil.getUsername());
        return Result.success("用户信息修改成功");
    }

    @Override
    public Result delete(Long id) {
        SysUser user=  sysUserMapper.findById(id);
        if (user==null) {
            return Result.fail("用户id不存在");
        }
        sysUserMapper.delete(id);
        sysUserMapper.deleteRolesByUserId(id);
        return Result.success("删除用户信息成功");
    }

    @Override
    public void updatePwdByMail(String email, String password) {
        log.info("修改密码");
        sysUserMapper.updatePwdByMail(email,password);
    }

    @Override
    public Result updateByopenid(WxLoginVo wxLoginVo) {
        if(StringUtils.isEmpty(wxLoginVo.getOpenId())){
            return Result.fail("无openid");
        }
        sysUserMapper.updateByopenid(wxLoginVo);
        return Result.success("更新成功");
    }
}
