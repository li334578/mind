package com.example.mindjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mindjava.entity.User;

import java.util.Optional;

/**
 * @Date 2023-08-10 14:47
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
public interface UserService extends IService<User> {
    Optional<User> getByUsername(String username);

    /**
     * 根据id 增加loginCount值 更新 lastLoginTime
     */
    void incrementLoginCountAndUpdateLastLoginTimeById(Long id);
}
