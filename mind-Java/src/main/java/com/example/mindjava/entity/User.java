package com.example.mindjava.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mindjava.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Date 2023-08-10 13:22
 * @Description 用户实体类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseBean<User> {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 状态 1 激活 2 禁用
     */
    private Integer status;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录次数
     */
    private Integer loginCount;
    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<Role> roleList;

}
