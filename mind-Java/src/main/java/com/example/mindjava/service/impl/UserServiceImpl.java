package com.example.mindjava.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mindjava.entity.Role;
import com.example.mindjava.entity.User;
import com.example.mindjava.entity.UserRole;
import com.example.mindjava.mapper.UserMapper;
import com.example.mindjava.service.RoleService;
import com.example.mindjava.service.UserRoleService;
import com.example.mindjava.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Date 2023-08-10 14:47
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)));
    }

    /**
     * 根据id 增加loginCount值 更新 lastLoginTime
     */
    @Override
    public void incrementLoginCountAndUpdateLastLoginTimeById(Long id) {
        userMapper.incrementLoginCountAndUpdateLastLoginTimeById(id);
    }

    @Override
    public IPage<User> pageUserInfo(User user) {
        IPage<User> page = new Page<>(user.getCurrentPage(), user.getPageSize());
        IPage<User> pageResult = this.page(page, null);
        List<Long> userIdList = pageResult.getRecords().stream().map(User::getId).collect(Collectors.toList());
        if (CollUtil.isEmpty(userIdList)) return pageResult;
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, userIdList));
        Map<Long, List<UserRole>> userIdMapRoles = userRoleList.stream().collect(Collectors.groupingBy(UserRole::getUserId));
        // 查询role
        Map<Long, Role> roleMap = roleService.listByIds(userRoleList.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Role::getId, Function.identity(), (k1, k2) -> k1));
        // 封装到record中
        pageResult.getRecords().forEach(item -> {
            List<UserRole> userRoles = userIdMapRoles.get(item.getId());
            item.setRoleList(userRoles.stream().map(per -> roleMap.get(per.getRoleId())).collect(Collectors.toList()));
        });
        return pageResult;
    }
}
