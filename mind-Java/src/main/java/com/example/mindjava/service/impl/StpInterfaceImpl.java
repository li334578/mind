package com.example.mindjava.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mindjava.entity.Role;
import com.example.mindjava.entity.UserRole;
import com.example.mindjava.service.RoleService;
import com.example.mindjava.service.UserRoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 权限码暂时未使用
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<UserRole> userRoleMappingList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, loginId));
        List<Long> roleIdList = userRoleMappingList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        Collection<Role> roleList = roleService.listByIds(roleIdList);
        return roleList.stream().map(Role::getRoleCode).collect(Collectors.toList());
    }

}
