package com.example.mindjava.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mindjava.controller.dto.UpdateUserRole;
import com.example.mindjava.entity.UserRole;
import com.example.mindjava.mapper.UserRoleMapper;
import com.example.mindjava.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色关联表(UserRole)表服务实现类
 *
 * @author liwenbo
 * @since 2023-08-18 22:05:59
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    /**
     * 更新用户角色
     */
    @Transactional
    @Override
    public void updateRoleToUser(UpdateUserRole updateUserRole) {
        // 查询既存用户角色
        List<UserRole> userRoles = this.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, updateUserRole.getUserId()));
        Set<Long> historyRoleId = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<UserRole> willAdd = new ArrayList<>();
        List<Long> newRoleId = updateUserRole.getRoleIdList();
        for (Long roleId : newRoleId) {
            if (!historyRoleId.contains(roleId)) {
                // 需要添加的角色
                UserRole userRole = new UserRole();
                userRole.setUserId(updateUserRole.getUserId());
                userRole.setRoleId(roleId);
                willAdd.add(userRole);
            }
        }
        List<Long> willDelete = new ArrayList<>();
        // 需要删除的
        for (Long roleId : historyRoleId) {
            if (!newRoleId.contains(roleId)) {
                willDelete.add(roleId);
            }
        }
        if (CollUtil.isNotEmpty(willAdd)) {
            this.saveBatch(willAdd);
        }
        if (CollUtil.isNotEmpty(willDelete)) {
            this.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, updateUserRole.getUserId()).in(UserRole::getRoleId, willDelete));
        }
    }
}

