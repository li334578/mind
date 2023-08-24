package com.example.mindjava.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date 2023-08-23 14:30
 * @Description 给用户添加角色接口
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class UpdateUserRole {
    private Long userId;
    private List<Long> roleIdList;
}
