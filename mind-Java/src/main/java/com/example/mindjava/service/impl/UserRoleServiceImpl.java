package com.example.mindjava.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mindjava.mapper.UserRoleMapper;
import com.example.mindjava.entity.UserRole;
import com.example.mindjava.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表(UserRole)表服务实现类
 *
 * @author liwenbo
 * @since 2023-08-18 22:05:59
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

