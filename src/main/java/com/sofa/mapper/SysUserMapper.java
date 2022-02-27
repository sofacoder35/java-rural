package com.sofa.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sofa.entity.SysMenu;
import com.sofa.entity.SysPermission;
import com.sofa.entity.SysRole;
import com.sofa.entity.SysUser;
import com.sofa.utils.QueryInfo;
import com.sofa.vo.WxLoginVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface SysUserMapper {


    SysUser findByUsername(String username);

    /**
     * 根据用户id查角色
     * @param userId
     * @return
     */
    List<SysRole> findRoles(@Param("userId") Long userId);

    /**
     * 根据用户id查菜单信息
     * @param userID
     * @return
     */
    List<SysMenu> findMenus(@Param("userId") Long userID);

    /**
     * 获取子菜单
     * @param id 父级id
     * @param userId 用户id
     * @return
     */
    List<SysMenu> findChildrenMenu(@Param("id")Long id,@Param("userId")Long userId);

    /**
     * 根据用户id查权限数据
     * @param userID
     * @return
     */
    List<SysPermission> findPermissions(@Param("userId") Long userID);

    Page<SysUser> findPage(String queryInfo);

    void insert(SysUser user);


    void insertOpenid(String openid);

    void update(SysUser user);

    void delete (Long id);

    void insertUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);

    void deleteRolesByUserId(@Param("userId") Long userId);


    SysUser findById(Long id);

    @Update("update sys_user set password =#{password} where email=#{email}")
    void updatePwdByMail(@Param("email") String email,@Param("password") String password);


    void updateByopenid(WxLoginVo wxLoginVo);
}
