package com.example.mindjava.util;

import cn.dev33.satoken.stp.StpUtil;
import com.example.mindjava.entity.User;

/**
 * @Date 2023-08-11 14:42
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
public class StpInfoUtil {

    public static Long getLoginUserId() {
        return Long.parseLong((String) StpUtil.getLoginId());
    }

    public static User getLoginUser() {
        return (User) StpUtil.getSession().get("user");
    }
}
