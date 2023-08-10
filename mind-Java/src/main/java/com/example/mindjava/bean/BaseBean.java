package com.example.mindjava.bean;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Date 2023-08-10 14:28
 * @Description 基类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Data
public class BaseBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 数据更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 分页\列表 数据
     */
    @TableField(exist = false)
    private List<T> dataList;
    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer currentPage;
    /**
     * 每页显示条数
     */
    @TableField(exist = false)
    private Integer pageSize;
    /**
     * 总条数
     */
    @TableField(exist = false)
    private Integer total;
}
