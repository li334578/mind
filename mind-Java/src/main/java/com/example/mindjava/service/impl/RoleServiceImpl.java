package com.example.mindjava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mindjava.mapper.RoleMapper;
import com.example.mindjava.entity.Role;
import com.example.mindjava.service.RoleService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色表(Role)表服务实现类
 *
 * @author liwenbo
 * @since 2023-08-18 21:59:47
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private Redisson redisson;

    private static final String commonUserKey = "user:role:commonUser";

    @Override
    public Role getCommonUser() {
        RBucket<Role> bucket = redisson.getBucket(commonUserKey);
        if (bucket.get() != null) {
            return bucket.get();
        }
        Role role = this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleCode, "commonUser"));
        bucket.set(role);
        return role;
    }
}

