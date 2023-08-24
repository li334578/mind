package com.example.mindjava.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mindjava.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户角色关联表(UserRole)表数据库访问层
 *
 * @author liwenbo
 * @since 2023-08-18 22:05:59
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

