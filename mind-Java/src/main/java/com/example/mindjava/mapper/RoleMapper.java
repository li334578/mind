package com.example.mindjava.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mindjava.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表(Role)表数据库访问层
 *
 * @author liwenbo
 * @since 2023-08-18 21:59:47
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

