package com.example.mindjava.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mindjava.bean.BaseBean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表(Role)表实体类
 *
 * @author liwenbo
 * @since 2023-08-18 21:59:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_role")
public class Role extends BaseBean<Role> implements Serializable {
    private static final long serialVersionUID = 1L;
    //角色名称
    private String roleName;
    //角色代号
    private String roleCode;
}

