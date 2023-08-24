package com.example.mindjava.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mindjava.bean.BaseBean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联表(UserRole)表实体类
 *
 * @author liwenbo
 * @since 2023-08-18 22:05:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user_role")
public class UserRole extends BaseBean<UserRole> implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户id
    private Long userId;
    //角色id
    private Long roleId;
}

