package com.example.mindjava.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mindjava.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Date 2023-08-11 14:07
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_bubble")
public class Bubble extends BaseBean<User> {

    /**
     * 内容
     */
    private String content;
    /**
     * 类型 1 临时存储 2 长时存储 3 永久存储
     */
    private Integer type;
    /**
     * 创建人id
     */
    private Long masterId;
    /**
     * 权重值 越大优先级越高
     */
    private Integer priorityValue;
    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer isDelete;
}
