package com.example.mindjava.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Date 2023-08-10 14:35
 * @Description MP 自动填充
 * @Version 1.0.0
 * @Author liwenbo
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置创建时间
        setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        // 设置更新时间
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置更新时间
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
