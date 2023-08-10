package com.example.mindjava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mindjava.entity.User;
import com.example.mindjava.mapper.UserMapper;
import com.example.mindjava.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

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
}
