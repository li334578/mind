package com.example.mindjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mindjava.entity.Role;

/**
 * 角色表(Role)表服务接口
 *
 * @author liwenbo
 * @since 2023-08-18 21:59:47
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取普通用户角色
     */
    Role getCommonUser();

}

